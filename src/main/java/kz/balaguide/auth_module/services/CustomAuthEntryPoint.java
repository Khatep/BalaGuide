package kz.balaguide.auth_module.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.balaguide.auth_module.utils.ExceptionUtil;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private final ResponseMetadataService responseMetadataService;
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Throwable rootCause = ExceptionUtil.getRootCause(authException);
        log.error("Error in filter chain: {}", rootCause.getMessage(), rootCause);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ResponseMetadata responseMetadata;
        if (rootCause instanceof ExpiredJwtException) {
            responseMetadata = responseMetadataService.findByCode(ResponseCode._0003);
        }
        else {
            //TODO Хардкод
            responseMetadata = responseMetadataService.findByCode(ResponseCode._0006);
        }

        ApiResponse<Void> errorResponse;
        errorResponse = new ApiResponse<>(responseMetadata, null);

        log.error("error response: {}", errorResponse);

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}


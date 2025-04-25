package kz.balaguide.common_module.exceptionhandling;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${server.error.path:/error}")
public class CustomErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private final ResponseMetadataService responseMetadataService;

    @RequestMapping
    public ResponseEntity<ApiResponse<String>> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String uri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        log.warn("Unhandled error ({}): {}", statusCode, uri);

        if (throwable instanceof ExpiredJwtException) {
            ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0003);
            ApiResponse<String> apiResponse = new ApiResponse<>(responseMetadata, throwable.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
        } else if (throwable instanceof MalformedJwtException) {
            ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0007);
            ApiResponse<String> apiResponse = new ApiResponse<>(responseMetadata, throwable.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
        }

        if (statusCode != null && statusCode == HttpStatus.NOT_FOUND.value()) {
            var metadata = ResponseMetadata.builder()
                    .id((long) HttpStatus.NOT_FOUND.value())
                    .responseCode(null)
                    .message("Endpoint not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(metadata, null));
        }

        var metadata = ResponseMetadata.builder()
                .id(500L)
                .responseCode(ResponseCode._0001)
                .message("Unexpected error")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(metadata, null));
    }
}


package kz.balaguide.auth_module.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.balaguide.auth_module.services.AuthUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.auth_module.services.JwtService;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    private final JwtService jwtService;
    private final AuthUserService authUserService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER_NAME);
        if ((authHeader == null || authHeader.isEmpty()) || !authHeader.startsWith(BEARER_PREFIX)) {
            try {
                filterChain.doFilter(request, response);
            } catch (InsufficientAuthenticationException e) {
                log.warn("Insufficient authentication: {}", e.getMessage());
            }
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());

        var phoneNumber = jwtService.extractPhoneNumber(jwt);

        if (!(phoneNumber == null || phoneNumber.isEmpty()) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;

            try {
                userDetails = authUserService.userDetailsService().loadUserByUsername(phoneNumber);
            } catch (RuntimeException e) {
                log.warn("An exception occurred for phoneNumber {}: {}", phoneNumber, e.getMessage());
            }

            // Authenticate if userDetails were found and token is valid
            if (userDetails != null && jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        log.info("{} {}", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}


package kz.balaguide.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.services.auth.JwtService;
import kz.balaguide.services.educationcenter.EducationCenterService;
import kz.balaguide.services.parent.ParentService;
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
    private final ParentService parentService;
    private final EducationCenterService educationCenterService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String uriPath = request.getRequestURI();

        var authHeader = request.getHeader(HEADER_NAME);
        if ((authHeader == null || authHeader.isEmpty()) || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var email = jwtService.extractEmail(jwt);

        if (!(email == null || email.isEmpty()) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;

            try {
                if (uriPath.contains("courses"))
                    userDetails = educationCenterService.userDetailsService().loadUserByUsername(email);
                else if (uriPath.contains("parents") || uriPath.contains("children") )
                    userDetails = parentService.userDetailsService().loadUserByUsername(email);
            } catch (RuntimeException e) {
                log.warn("An exception occurred for email {}: {}", email, e.getMessage());
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


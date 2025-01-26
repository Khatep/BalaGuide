package kz.balaguide.utils.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface GetUsernameFromSecurityContextHolder {
    static String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userdetails) {
            return userdetails.getUsername();
        } else {
            return principal.toString();
        }
    }
}

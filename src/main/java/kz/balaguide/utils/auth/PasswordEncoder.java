package kz.balaguide.utils.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface PasswordEncoder {
    static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}

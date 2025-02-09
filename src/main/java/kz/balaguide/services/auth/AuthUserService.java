package kz.balaguide.services.auth;

import kz.balaguide.core.entities.AuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService {

    UserDetailsService userDetailsService() throws UsernameNotFoundException;

    void save(AuthUser authUser);

    void checkIsUserWithPhoneNumberAlreadyExists(String phoneNumber);
}

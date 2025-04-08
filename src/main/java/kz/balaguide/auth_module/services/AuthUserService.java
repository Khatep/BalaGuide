package kz.balaguide.auth_module.services;

import kz.balaguide.auth_module.dtos.JwtResponseDto;
import kz.balaguide.auth_module.dtos.SignInUserResponse;
import kz.balaguide.common_module.core.entities.AbstractEntity;
import kz.balaguide.common_module.core.entities.AuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService {

    UserDetailsService userDetailsService() throws UsernameNotFoundException;

    void save(AuthUser authUser);

    void checkIsUserWithPhoneNumberAlreadyExists(String phoneNumber);

    SignInUserResponse<AbstractEntity> signIn(AuthUser user, JwtResponseDto jwtResponseDto);
}

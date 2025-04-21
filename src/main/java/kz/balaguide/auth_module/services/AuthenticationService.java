package kz.balaguide.auth_module.services;


import kz.balaguide.auth_module.dtos.SignInUserRequest;
import kz.balaguide.auth_module.dtos.SignInUserResponse;
import kz.balaguide.auth_module.dtos.SignUpUserRequest;
import kz.balaguide.common_module.core.entities.AbstractEntity;
import kz.balaguide.common_module.core.entities.AuthUser;
import lombok.RequiredArgsConstructor;
import kz.balaguide.auth_module.dtos.JwtResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static kz.balaguide.auth_module.utils.PasswordEncoder.encodePassword;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthUserService authUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDto signUpUser(SignUpUserRequest signUpUserRequest) {

        authUserService.checkIsUserWithPhoneNumberAlreadyExists(signUpUserRequest.phoneNumber());

        AuthUser authUser = AuthUser.builder()
                .phoneNumber(signUpUserRequest.phoneNumber())
                .password(encodePassword(signUpUserRequest.password()))
                .role(signUpUserRequest.role())
                .build();

        authUserService.save(authUser);

        return jwtService.generateToken(authUser);
    }

    /**
     * Sign in of all types of users
     *
     * @param request data of user
     * @return {@link JwtResponseDto} which contain token
     */
    public SignInUserResponse<AbstractEntity> signIn(SignInUserRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.phoneNumber(),
                request.password()
        ));

        AuthUser user = (AuthUser) authUserService
                .userDetailsService()
                .loadUserByUsername(request.phoneNumber());

        return authUserService.signIn(user, jwtService.generateToken(user));
    }
}
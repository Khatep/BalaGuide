package kz.balaguide.services.auth;


import kz.balaguide.core.dtos.auth.SignInUserRequest;
import kz.balaguide.core.dtos.auth.SignUpUserRequest;
import kz.balaguide.core.entities.AuthUser;
import lombok.RequiredArgsConstructor;
import kz.balaguide.core.dtos.auth.JwtAuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static kz.balaguide.utils.auth.PasswordEncoder.encodePassword;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthUserService authUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUpUser(SignUpUserRequest signUpUserRequest) {

        authUserService.checkIsUserWithPhoneNumberAlreadyExists(signUpUserRequest.phoneNumber());

        AuthUser authUser = AuthUser.builder()
                .phoneNumber(signUpUserRequest.phoneNumber())
                .password(encodePassword(signUpUserRequest.password()))
                .role(signUpUserRequest.role())
                .build();

        authUserService.save(authUser);

        var jwt = jwtService.generateToken(authUser);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Sign in of all types of users
     *
     * @param request data of user
     * @return {@link JwtAuthenticationResponse} which contain token
     */
    public JwtAuthenticationResponse signIn(SignInUserRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.phoneNumber(),
                request.password()
        ));

        var user = authUserService
                .userDetailsService()
                .loadUserByUsername(request.phoneNumber());
        String jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }
}
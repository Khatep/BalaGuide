package kz.balaguide.auth_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.auth_module.dtos.JwtAuthenticationResponse;
import kz.balaguide.auth_module.dtos.SignInUserRequest;
import kz.balaguide.auth_module.dtos.SignUpUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.auth_module.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ForLog
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpUserRequest request) {
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signUpUser(request);
        //TODO: delete next log, cuz token must be confidential
        //log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthenticationResponse);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInUserRequest request) {
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signIn(request);
        //TODO: delete next log, cuz token must be confidential
        //log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return authenticationService.signIn(request);
    }
}

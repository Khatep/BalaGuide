package org.khatep.balaguide.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.security.dto.JwtAuthenticationResponse;
import org.khatep.balaguide.security.dto.SignInRequest;
import org.khatep.balaguide.security.dto.SignUpEduCenterRequest;
import org.khatep.balaguide.security.dto.SignUpParentRequest;
import org.khatep.balaguide.security.service.AuthenticationService;
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
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpParentRequest request) {
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signUpParent(request);
        log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthenticationResponse);
    }

    @PostMapping("/sign-up-edu-center")
    public ResponseEntity<JwtAuthenticationResponse> signUpEducationCenter(@RequestBody @Valid SignUpEduCenterRequest request) {
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signUpEducationCenter(request);
        log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthenticationResponse);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signIn(request);
        log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return authenticationService.signIn(request);
    }
}

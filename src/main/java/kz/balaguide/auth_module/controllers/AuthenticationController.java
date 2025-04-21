package kz.balaguide.auth_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.auth_module.dtos.JwtResponseDto;
import kz.balaguide.auth_module.dtos.SignInUserRequest;
import kz.balaguide.auth_module.dtos.SignInUserResponse;
import kz.balaguide.auth_module.dtos.SignUpUserRequest;
import kz.balaguide.common_module.core.entities.AbstractEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<JwtResponseDto> signUp(@RequestBody @Valid SignUpUserRequest request) {
        JwtResponseDto jwtResponseDto = authenticationService.signUpUser(request);
        //log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInUserResponse<AbstractEntity>> signIn(@RequestBody @Valid SignInUserRequest request) {
        SignInUserResponse<AbstractEntity> jwtResponseDto = authenticationService.signIn(request);
        //log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDto);
    }
}

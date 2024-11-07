package org.khatep.balaguide.security.service;


import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.models.entities.EducationCenter;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.models.enums.Role;
import org.khatep.balaguide.repositories.EducationCenterRepository;
import org.khatep.balaguide.security.dto.JwtAuthenticationResponse;
import org.khatep.balaguide.security.dto.SignInRequest;
import org.khatep.balaguide.security.dto.SignUpEduCenterRequest;
import org.khatep.balaguide.security.dto.SignUpParentRequest;
import org.khatep.balaguide.services.EducationCenterService;
import org.khatep.balaguide.services.ParentService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ParentService parentService;
    private final EducationCenterService educationService;
    private final EducationCenterRepository educationCenterRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Parent registration
     *
     * @param request data of parent
     * @return {@link JwtAuthenticationResponse} which contain token
     */
    public JwtAuthenticationResponse signUpParent(SignUpParentRequest request) {

        Parent parent = Parent.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .balance(new BigDecimal("0.0"))
                .role(Role.PARENT)
                .build();

        parentService.save(parent);

        var jwt = jwtService.generateToken(parent);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Education center registration
     *
     * @param request data of educationCenter
     * @return {@link JwtAuthenticationResponse} which contain token
     */
    public JwtAuthenticationResponse signUpEducationCenter(SignUpEduCenterRequest request) {

        EducationCenter educationCenter = EducationCenter.builder()
                .name(request.getName())
                .dateOfCreated(request.getDateOfCreated())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .instagramLink(request.getInstagramLink())
                .balance(new BigDecimal("0.0"))
                .role(Role.EDUCATION_CENTER)
                .build();

        educationCenterRepository.save(educationCenter);

        var jwt = jwtService.generateToken(educationCenter);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Auth of all types of users
     *
     * @param request data of user
     * @return {@link JwtAuthenticationResponse} which contain token
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        String jwt = null;
        if (request.getRole().equals(Role.PARENT)) {
            var user = parentService
                    .userDetailsService()
                    .loadUserByUsername(request.getEmail());
            jwt = jwtService.generateToken(user);
        }
        else if (request.getRole().equals(Role.EDUCATION_CENTER)) {
            var user = educationService
                    .userDetailsService()
                    .loadUserByUsername(request.getEmail());
            jwt = jwtService.generateToken(user);
        }

        return new JwtAuthenticationResponse(jwt);
    }
}
package kz.balaguide.services.auth;


import kz.balaguide.services.educationcenter.EducationCenterService;
import kz.balaguide.services.parent.ParentService;
import lombok.RequiredArgsConstructor;
import kz.balaguide.core.entities.EducationCenter;
import kz.balaguide.core.entities.Parent;
import kz.balaguide.core.enums.Role;
import kz.balaguide.core.repositories.educationcenter.EducationCenterRepository;
import kz.balaguide.core.dtos.auth.JwtAuthenticationResponse;
import kz.balaguide.core.dtos.auth.SignInRequest;
import kz.balaguide.core.dtos.auth.SignUpEduCenterRequest;
import kz.balaguide.core.dtos.auth.SignUpParentRequest;
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
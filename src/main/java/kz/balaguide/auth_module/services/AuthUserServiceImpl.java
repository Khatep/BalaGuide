package kz.balaguide.auth_module.services;

import kz.balaguide.auth_module.dtos.JwtResponseDto;
import kz.balaguide.auth_module.dtos.SignInUserResponse;
import kz.balaguide.auth_module.repository.AuthUserRepository;
import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.common_module.core.entities.AbstractEntity;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.EducationCenterNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.TeacherNotFoundException;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import kz.balaguide.parent_module.repository.ParentRepository;
import kz.balaguide.teacher_module.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository authUserRepository;
    //TODO hardcode
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final EducationCenterRepository educationCenterRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return this::getByPhoneNumber;
    }

    //TODO add UsernameNotFoundException to GlobalExceptionHandler
    private AuthUser getByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        return authUserRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
    }

    @Override
    public void save(AuthUser authUser) {
        authUserRepository.save(authUser);
    }

    @Override
    public void checkIsUserWithPhoneNumberAlreadyExists(String phoneNumber) {
        Optional<AuthUser> authUser = authUserRepository.findByPhoneNumber(phoneNumber);

        if (authUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with phone number: " + phoneNumber);
        }
    }

    @Override
    public SignInUserResponse<AbstractEntity> signIn(AuthUser authUser, JwtResponseDto jwtResponseDto) {
        AbstractEntity clientData = null;
        switch (authUser.getRole()) {
            case PARENT -> clientData = parentRepository.findByPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new ParentNotFoundException("Parent not found with phone number: " + authUser.getPhoneNumber()));
            case CHILD -> clientData = childRepository.findByPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new ChildNotFoundException("Child not found with phone number: " + authUser.getPhoneNumber()));
            case EDUCATION_CENTER -> clientData = educationCenterRepository.findByPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new EducationCenterNotFoundException("Education center not found with phone number: " + authUser.getPhoneNumber()));
            case TEACHER -> clientData = teacherRepository.findByPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with phone number: " + authUser.getPhoneNumber()));
            default -> throw new RuntimeException("Unknown role: " + authUser.getRole());
        }

        log.info(clientData.toString());
        return SignInUserResponse.<AbstractEntity>builder()
                .jwtResponseDto(jwtResponseDto)
                .user(clientData)
                .build();
    }
}

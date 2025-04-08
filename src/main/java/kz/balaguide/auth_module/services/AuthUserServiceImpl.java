package kz.balaguide.auth_module.services;

import kz.balaguide.auth_module.dtos.JwtResponseDto;
import kz.balaguide.auth_module.dtos.SignInUserResponse;
import kz.balaguide.child_module.services.ChildService;
import kz.balaguide.common_module.core.entities.AbstractEntity;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.auth_module.repository.AuthUserRepository;
import kz.balaguide.parent_module.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository authUserRepository;
    //TODO hardcode
    private final ParentService parentService;
    private final ChildService childService;
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
        AbstractEntity clientData;
        switch (authUser.getRole()) {
            case PARENT -> clientData = parentService.findByPhoneNumber(authUser.getPhoneNumber());
            case CHILD -> clientData = childService.findByPhoneNumber(authUser.getPhoneNumber());
            default -> clientData = null;
        }

        return SignInUserResponse.<AbstractEntity>builder()
                .jwtResponseDto(jwtResponseDto)
                .user(clientData)
                .build();
    }
}

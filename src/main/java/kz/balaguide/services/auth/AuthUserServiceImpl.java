package kz.balaguide.services.auth;

import kz.balaguide.core.entities.AuthUser;
import kz.balaguide.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.core.repositories.auth.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository authUserRepository;

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
}

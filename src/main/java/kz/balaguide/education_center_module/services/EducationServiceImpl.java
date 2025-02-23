package kz.balaguide.education_center_module.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationServiceImpl implements EducationCenterService {

    private final EducationCenterRepository educationCenterRepository;

    /**
     * Provides a custom {@link UserDetailsService} implementation for Spring Security
     * that retrieves user details based on the user's email.
     * <p>
     * This method overrides the default {@code userDetailsService} to enable email-based
     * authentication, replacing the typical username-based approach.
     * <p>
     * It uses the {@code getByEmail} method to find and return the user details.
     *
     * @return a {@link UserDetailsService} instance that retrieves user details by email.
     */

    private EducationCenter getByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        return educationCenterRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Education center with phone number: " + phoneNumber + " not found"));
    }
}

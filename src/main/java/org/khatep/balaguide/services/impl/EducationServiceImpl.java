package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khatep.balaguide.models.entities.EducationCenter;
import org.khatep.balaguide.repositories.EducationCenterRepository;
import org.khatep.balaguide.services.EducationCenterService;
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
    @Override
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return this::getByEmail;
    }

    private EducationCenter getByEmail(String email) throws UsernameNotFoundException {
        return educationCenterRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Education center with email: " + email + " not found"));
    }
}

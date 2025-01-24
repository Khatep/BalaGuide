package org.khatep.balaguide.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface EducationCenterService {

    UserDetailsService userDetailsService() throws UsernameNotFoundException;

}

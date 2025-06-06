package kz.balaguide.education_center_module.services;

import kz.balaguide.auth_module.services.AuthUserService;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.education_center_module.dtos.*;
import kz.balaguide.education_center_module.mappers.EducationCenterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationServiceImpl implements EducationCenterService {
    private final AuthUserService authUserService;
    private final EducationCenterRepository educationCenterRepository;
    private final EducationCenterMapper educationCenterMapper;

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

    //TODO Реализую позже
    @Override
    public EducationCenter createEducationCenter(EducationCenterCreateReq educationCenterCreateReq) {
        if (educationCenterRepository.existsByEmail(educationCenterCreateReq.email())) {
            log.warn("Education center with email: {} already exists", educationCenterCreateReq.email());
            throw new UserAlreadyExistsException("Education center with email: " + educationCenterCreateReq.email() + " already exists");
        }

        if (educationCenterRepository.existsByPhoneNumber(educationCenterCreateReq.phoneNumber())) {
            log.warn("Education center with phoneNumber: {} already exists", educationCenterCreateReq.phoneNumber());
            throw new UserAlreadyExistsException("Education center with phoneNumber: " + educationCenterCreateReq.phoneNumber() + " already exists");
        }

        EducationCenter educationCenter = educationCenterMapper.mapEducationCenterCreateReqToEducationCenter(educationCenterCreateReq);

        AuthUser authUser = (AuthUser) authUserService
                .userDetailsService()
                .loadUserByUsername(educationCenterCreateReq.phoneNumber());
        educationCenter.setAuthUser(authUser);

        return educationCenterRepository.save(educationCenter);
    }

    @Override
    public Double calculateTotalRevenue(Long centerId) {
        return educationCenterRepository.calculateTotalRevenue(centerId);
    }

    @Override
    public List<CourseRevenueDTO> getTopCoursesByRevenue(Long centerId, int limit) {
        return educationCenterRepository.getTopCoursesByRevenue(centerId, limit).stream()
                .map(result -> CourseRevenueDTO.builder()
                        .courseName(result.get("courseName").toString())
                        .revenue(Double.valueOf(result.get("revenue").toString()))
                        .build())
                .toList();
    }

    @Override
    public List<CourseChildrenDTO> getChildrenDistributionByCourse(Long centerId) {
        return educationCenterRepository.getChildrenDistributionByCourse(centerId).stream()
                .map(result -> CourseChildrenDTO.builder()
                        .courseName(result.get("courseName").toString())
                        .childrenCount(Integer.valueOf(result.get("childrenCount").toString()))
                        .build())
                .toList();
    }

    @Override
    public List<MonthlyRevenueDTO> getMonthlyRevenue(Long centerId) {
        return educationCenterRepository.getMonthlyRevenue(centerId).stream()
                .map(result -> MonthlyRevenueDTO.builder()
                        .month(YearMonth.parse(result.get("month").toString()))
                        .revenue(Double.valueOf(result.get("revenue").toString()))
                        .build())
                .toList();
    }

    @Override
    public List<MonthlyChildrenGrowthDTO> getMonthlyChildrenGrowth(Long centerId) {
        return educationCenterRepository.getMonthlyChildrenGrowth(centerId).stream()
                .map(result -> MonthlyChildrenGrowthDTO.builder()
                        .month(YearMonth.parse(result.get("month").toString()))
                        .childrenCount(Integer.valueOf(result.get("childrenCount").toString()))
                        .build())
                .toList();
    }

    @Override
    public Double calculateAverageCourseDuration(Long centerId) {
        return educationCenterRepository.calculateAverageCourseDuration(centerId);
    }

    @Override
    public Double calculateAverageGroupFillPercent(Long centerId) {
        return educationCenterRepository.calculateAverageGroupFillPercent(centerId);
    }

    @Override
    public Integer countReturningParents(Long centerId) {
        return educationCenterRepository.countReturningParents(centerId);
    }

}

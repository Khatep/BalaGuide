package kz.balaguide.education_center_module.services;

import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.education_center_module.dtos.*;

import java.util.List;

public interface EducationCenterService {
    EducationCenter createEducationCenter(EducationCenterCreateReq educationCenterCreateReq);
    Double calculateTotalRevenue(Long centerId);

    List<CourseRevenueDTO> getTopCoursesByRevenue(Long centerId, int limit);

    List<CourseChildrenDTO> getChildrenDistributionByCourse(Long centerId);

    List<MonthlyRevenueDTO> getMonthlyRevenue(Long centerId);

    List<MonthlyChildrenGrowthDTO> getMonthlyChildrenGrowth(Long centerId);

    Double calculateAverageCourseDuration(Long centerId);

    Double calculateAverageGroupFillPercent(Long centerId);

    Integer countReturningParents(Long centerId);

}

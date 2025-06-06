package kz.balaguide.education_center_module.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseRevenueDTO {
    private String courseName;
    private Double revenue;
}

package kz.balaguide.education_center_module.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseChildrenDTO {
    private String courseName;
    private Integer childrenCount;
}

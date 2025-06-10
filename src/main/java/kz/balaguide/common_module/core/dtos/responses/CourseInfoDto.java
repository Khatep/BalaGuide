package kz.balaguide.common_module.core.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseInfoDto {
    private Long courseId;
    private String courseName;
    private String groupName;
    private String language;
    //private String teacherFullName;
}
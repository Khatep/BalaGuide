package kz.balaguide.common_module.core.dtos.responses;


import kz.balaguide.common_module.core.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ChildDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private Gender gender;
    private String parentFullName;
    private Long parentId;

    private List<CourseInfoDto> enrolledCourses; // новое поле
}


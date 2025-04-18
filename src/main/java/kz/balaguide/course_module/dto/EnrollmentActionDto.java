package kz.balaguide.course_module.dto;

public record EnrollmentActionDto(
        Long parentId,
        Long courseId,
        Long groupId,
        Long childId,
        String methodType
) {}

package kz.balaguide.course_module.dto;

public record CreateGroupRequest(
    String name,
    Long courseId,
    Long teacherId,
    int maxParticipants,
    int minParticipants,
    String language
) {}

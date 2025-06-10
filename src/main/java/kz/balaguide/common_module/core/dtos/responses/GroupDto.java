package kz.balaguide.common_module.core.dtos.responses;

import kz.balaguide.common_module.core.entities.Course;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GroupDto(
        Long id,
        String name,
        int maxParticipants,
        int minParticipants,
        int currentParticipants,
        LocalDate startEducationDate,
        String language,
        Course course,
        List<ChildDto> childrenEnrolled
) {}

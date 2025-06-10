package kz.balaguide.course_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleConflictDto {
    private String conflictType; // TEACHER, GROUP, ROOM
    private String message;
    private ScheduleItemDto conflictingLesson;
}


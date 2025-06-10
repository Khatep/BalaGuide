package kz.balaguide.course_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDto {
    private Long scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private Integer durationMinutes;
    private Boolean isAvailable;
    private String conflictReason;
    private String timeZone;
}

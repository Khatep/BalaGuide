package kz.balaguide.course_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItemDto {
    private Long lessonId;
    private Integer lessonNumber;
    private String topic;
    private String description;
    private String courseName;
    private String groupName;
    private String teacherName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private Integer durationMinutes; // вычисляется из startTime и endTime
    private Integer childrenCount;
    private String color; // для фронтенда
    private Boolean hasFile;
    private Long scheduleId;
    private String timeZone;
}


package kz.balaguide.course_module.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduledLessonRequest {

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Lesson number is required")
    private Integer lessonNumber;

    private String topic;
    private String description;
    private String fileUrl;
}

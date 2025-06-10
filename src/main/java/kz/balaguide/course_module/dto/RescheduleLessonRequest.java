package kz.balaguide.course_module.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RescheduleLessonRequest {

    @NotNull(message = "New date is required")
    private LocalDate newDate;

    private Long newScheduleId; // Если меняем время
    private String reason;
}
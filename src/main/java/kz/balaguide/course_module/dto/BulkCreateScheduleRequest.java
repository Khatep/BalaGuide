package kz.balaguide.course_module.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkCreateScheduleRequest {

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotEmpty(message = "At least one lesson must be provided")
    private List<LessonTemplateDto> lessons;

    // Опциональные параметры
    private Integer repeatEveryNWeeks; // Повторять каждые N недель
    private List<LocalDate> excludeDates; // Исключить даты (праздники и т.д.)
}

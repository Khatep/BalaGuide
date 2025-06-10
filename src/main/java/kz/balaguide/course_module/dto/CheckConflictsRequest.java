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
public class CheckConflictsRequest {

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    private Long groupId;
    private Long educationCenterId;

    // Если проверяем конфликты для существующего урока
    private Long excludeLessonId;
}


package kz.balaguide.course_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleFilterDto {
    private Long educationCenterId;
    private Long teacherId;
    private Long groupId;
    private Long courseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}


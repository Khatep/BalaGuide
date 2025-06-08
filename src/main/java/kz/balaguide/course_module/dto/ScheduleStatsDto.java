package kz.balaguide.course_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleStatsDto {
    private Integer totalLessons;
    private Integer totalStudents;
    private Integer totalTeachers;
    private Integer totalGroups;
    private Double averageClassSize;
    private Integer completedLessons;
    private Integer cancelledLessons;
    private Integer upcomingLessons;
}

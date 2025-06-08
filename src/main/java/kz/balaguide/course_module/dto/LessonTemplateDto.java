package kz.balaguide.course_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonTemplateDto {
    private Integer lessonNumber;
    private String topic;
    private String description;
    private String fileUrl;
}

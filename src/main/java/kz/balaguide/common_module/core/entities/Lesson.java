package kz.balaguide.common_module.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lessons")
public class Lesson extends AbstractEntity {
    @Column(name = "lesson_number", nullable = false, unique = true)
    private Integer lessonNumber;

    private String topic; //linked to the course's content field (key)

    private String description; //linked to the course's content field (value)

    @Column(name = "file_url")
    private String fileUrl; //Link to google disk

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @ToString.Exclude
    private Group group; //Group contains course, teacher, students

    private LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}

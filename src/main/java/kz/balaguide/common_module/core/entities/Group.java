package kz.balaguide.common_module.core.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group")
public class Group extends AbstractEntity {

    @NotNull(message = "Group name must not be null")
    private String name;

    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Teacher teacher;

    /**
     * The minimum number of participants for the course.
     */
    @NotNull(message = "Min participants must not be null")
    @Positive(message = "Min participants must be greater than zero")
    @Column(name = "min_participants", nullable = false)
    private int minParticipants;

    /**
     * The maximum number of participants for the course.
     */
    @NotNull(message = "Max participants must not be null")
    @Positive(message = "Max participants must be greater than zero")
    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    /**
     * The current number of participants in the course.
     */
    @NotNull(message = "Current participants must not be null")
    @Column(name = "current_participants", nullable = false)
    private int currentParticipants;

    /**
     * EN, RU, KZ
     * */
    private String language;

    //NOT now private Schedule schedule
}

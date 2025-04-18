package kz.balaguide.common_module.core.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "groups")
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

    private boolean groupFull;

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
    private int currentParticipants = 0;

    /**
     * EN, RU, KZ
     * */
    private String language;

    @ManyToMany(mappedBy = "groupsEnrolled", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Child> childrenEnrolled = new ArrayList<>();

    //NOT now: private Schedule schedule
}

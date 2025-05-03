package kz.balaguide.common_module.core.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
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

    /**
     * The maximum number of participants for the course.
     */
    @NotNull(message = "Max participants must not be null")
    @Positive(message = "Max participants must be greater than zero")
    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    /**
     * The minimum number of participants for the course.
     */
    @NotNull(message = "Min participants must not be null")
    @Positive(message = "Min participants must be greater than zero")
    @Column(name = "min_participants", nullable = false)
    private int minParticipants;

    /**
     * The current number of participants in the course.
     */
    @NotNull(message = "Current participants must not be null")
    @Column(name = "current_participants", nullable = false)
    private int currentParticipants = 0;

    @Column(name = "group_full", nullable = false)
    private boolean groupFull = false;

    private LocalDate startEducationDate;

    /**
     * EN, RU, KZ
     * */
    private String language;

    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Teacher teacher;

    @ManyToMany(mappedBy = "groupsEnrolled", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<Child> childrenEnrolled = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<Lesson> lessons = new ArrayList<>();
    //NOT now: private Schedule schedule
}

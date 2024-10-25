package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.khatep.balaguide.models.enums.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course implements Comparable<Course> {

    /**
     * The unique identifier of the course.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * The name of the course.
     */
    private String name;

    /**
     * A brief description of the course.
     */
    private String description;

    /**
     * The category of the course: Programming, Sport, Languages, Art, Math
     */
    @Enumerated(EnumType.STRING)
    private Category category;

    /**
     * The age range suitable for the course (e.g., "6-10 years").
     */
    private String ageRange;

    /**
     * The price of the course.
     */
    private BigDecimal price;

    /**
     * The number of weeks of course duration.
     * */
    private Integer durability;
    
    /**
     * The physical address of the course.
     */
    private String address;

    /**
     * The maximum number of participants for the course.
     */
    @Column(nullable = false)
    private int maxParticipants;
    
    /**
     * The current number of participants in the course.
     */
    @Column(nullable = false)
    private int currentParticipants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_center_id")
    @ToString.Exclude
    private EducationCenter educationCenter;

    @ManyToMany(mappedBy = "myCourses", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ToString.Exclude
    List<Teacher> teachers;

    @ManyToMany(mappedBy = "coursesEnrolled", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    List<Child> children;

    @Override
    public int compareTo(Course o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Course course = (Course) o;
        return getId() != null && Objects.equals(getId(), course.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

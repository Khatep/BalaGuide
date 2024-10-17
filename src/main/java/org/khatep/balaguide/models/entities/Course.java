package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Course implements Comparable<Course> {

    /**
     * The unique identifier of the course.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the course.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * A brief description of the course.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * The name of the educational center providing the course.
     */
    @Column(name = "educational_center", nullable = false)
    private String educationalCenter;

    /**
     * The category of the course (e.g., Art, Science, Sports).
     */
    @Column(name = "category", nullable = false)
    private String category;

    /**
     * The age range suitable for the course (e.g., "6-10 years").
     */
    @Column(name = "age_range", nullable = false)
    private String ageRange;

    /**
     * The price of the course.
     */
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * The physical address of the course.
     */
    @Column(name = "address")
    private String address;

    /**
     * The maximum number of participants for the course.
     */
    @Column(name = "max_participants")
    private int maxParticipants;

    /**
     * The current number of participants in the course.
     */
    @Column(name = "current_participants")
    private int currentParticipants;

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

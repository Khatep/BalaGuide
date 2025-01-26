package kz.balaguide.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import kz.balaguide.core.enums.Category;

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
    private Long id;

    /**
     * The name of the course.
     */
    @NotNull(message = "Name must be not null")
    @NotBlank(message = "Name must be not empty")
    private String name;

    /**
     * A brief description of the course.
     */
    @NotNull(message = "Description must be not null")
    @NotBlank(message = "Description must be not empty")
    private String description;

    /**
     * The category of the course: Programming, Sport, Languages, Art, Math
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category must be not null")
    private Category category;

    /**
     * The age range suitable for the course (e.g., "6-10 years").
     */
    @NotNull(message = "Age range must be not null")
    @Pattern(regexp = "^\\d{1,2}-\\d{1,2}", message = "Age range must be in the format 'X-Y', where X and Y are numbers")
    private String ageRange;

    /**
     * The price of the course.
     */
    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    /**
     * The number of weeks of course duration.
     * */
    @NotNull(message = "Durability must not be null")
    @Positive(message = "Durability must be greater than zero")
    private Integer durability;

    /**
     * The maximum number of participants for the course.
     */
    @NotNull(message = "Max participants must not be null")
    @Positive(message = "Max participants must be greater than zero")
    private int maxParticipants;
    
    /**
     * The current number of participants in the course.
     */
    @NotNull(message = "Current participants must not be null")
    private int currentParticipants;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_center_id")
    @ToString.Exclude
    private EducationCenter educationCenter;

    @ManyToMany(mappedBy = "myCourses", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "coursesEnrolled", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Child> children;

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

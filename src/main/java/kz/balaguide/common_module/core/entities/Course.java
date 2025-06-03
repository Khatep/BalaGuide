package kz.balaguide.common_module.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import kz.balaguide.common_module.core.enums.CourseCategory;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course extends AbstractEntity implements Comparable<Course> {
    /**
     * The name of the course.
     */
    @NotNull(message = "Name must be not null")
    @NotBlank(message = "Name must be not empty")
    @Column(name = "course_name", length = 100)
    private String name;

    /**
     * A brief description of the course.
     */
    @NotNull(message = "Description must be not null")
    @NotBlank(message = "Description must be not empty")
    @Column(name = "description", length = 500)
    private String description;

    /**
     * The courseCategory of the course: Programming, Sport, Languages, Art, Math
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "CourseCategory must be not null")
    @Column(name = "course_category")
    private CourseCategory courseCategory;

    /**
     * The age range suitable for the course (e.g., "6-10 years").
     */
    @NotNull(message = "Age range must be not null")
    @Pattern(regexp = "^\\d{1,2}-\\d{1,2}", message = "Age range must be in the format 'X-Y', where X and Y are numbers")
    @Column(name = "age_range")
    private String ageRange;

    /**
     * The price of the course.
     */
    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    /**
     * The number of weeks of course duration.
     */
    //TODO может сделаем Lessons
    @NotNull(message = "Number of lessons must not be null")
    @Positive(message = "Number of lessons must be greater than zero")
    @Column(name = "number_of_lessons_in_week")
    private Integer numberOfLessonsInWeek;

    @Column(name = "durability_by_weeks")
    private Integer durabilityByWeeks;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_center_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private EducationCenter educationCenter;

    @ManyToMany(mappedBy = "myCourses", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    @JsonIgnore
    private List<Group> groups;

    //TODO endpoint for content creation and content updating
    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private Map<Integer, Map<String, String>> content = new HashMap<>();
    //key: lesson's topic, value: lesson's description
    /*
    {
        "1": {
            "Java Intro": "Lesson for introduction to Java lang, educate history of Java"
        },
        "2": {
            "Python Intro": "Lesson for introduction to Python lang, educate history of Python"
        }

    }
    * */

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

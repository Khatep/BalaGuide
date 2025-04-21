package kz.balaguide.common_module.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kz.balaguide.common_module.core.enums.Gender;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teachers")
public class Teacher extends AbstractEntity implements Comparable<Teacher> {
    /**
     * The first name of the teacher.
     */
    @NotNull(message = "First name must be not null")
    @NotBlank(message = "First name must be not empty")
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the teacher.
     */
    @NotNull(message = "Last name must be not null")
    @NotBlank(message = "Last name must be not empty")
    @Column(name = "last_name")
    private String lastName;

    /**
     * The date of birth of the teacher.
     */
    @NotNull(message = "Birth date must be not null")
    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * The contact phone number of the teacher.
     */
    @NotNull(message = "Phone number must be not null")
    @NotBlank(message = "Phone number must be not empty")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
    @Column(name = "phone_number")
    private String phoneNumber;

    /** The email address of the teacher. */
    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email must be in format: user@example.com")
    @Column(unique = true)
    private String email;

    /**
     * The salary of the teacher.
     */
    @NotNull(message = "Salary must be not null")
    @Positive(message = "Salary must be greater than zero")
    private BigDecimal salary;

    /**
     * The gender of the teacher.
     */
    @NotNull(message = "Gender must not be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false, unique = true)
    @ToString.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AuthUser authUser;
    
    /**
     * The list of courses taught by the teacher.
     * This list may contain multiple {@link Course} objects.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore
    private List<Course> myCourses;

    @Override
    public int compareTo(Teacher o) {
        return getId().compareTo(o.getId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Teacher teacher = (Teacher) o;
        return getId() != null && Objects.equals(getId(), teacher.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}

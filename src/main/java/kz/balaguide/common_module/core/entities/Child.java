package kz.balaguide.common_module.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kz.balaguide.common_module.core.enums.Gender;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "child")
public class Child extends AbstractEntity implements Comparable<Child> {

    /** The first name of the child. */
    @NotNull(message = "First name must be not null")
    @NotBlank(message = "First name must be not empty")
    @Column(name = "first_name")
    private String firstName;

    /** The last name of the child. */
    @NotNull(message = "Last name must be not null")
    @NotBlank(message = "Last name must be not empty")
    @Column(name = "last_name")
    private String lastName;

    /** The phone number of the child. */
    @NotNull(message = "Phone number must be not null")
    @NotBlank(message = "Phone number must be not empty")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    /** The email address of the child. */
    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email must be in format: user@example.com")
    @Column(unique = true)
    private String email;

    /** The birthdate of the child. */
    @NotNull(message = "Birth date must be not null")
    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false, unique = true)
    @ToString.Exclude
    @JsonIgnore
    private AuthUser authUser;

    /** The gender of the child. */
    @NotNull(message = "Gender must not be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /** The parent associated with the child. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "parent_id", referencedColumnName = "id")
    private Parent parent;

    /** A list of courses the child is enrolled in. */
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "child_course",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @ToString.Exclude
    @Builder.Default
    private List<Course> coursesEnrolled = new ArrayList<>();

    @Override
    public int compareTo(Child o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Child child = (Child) o;
        return getId() != null && Objects.equals(getId(), child.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

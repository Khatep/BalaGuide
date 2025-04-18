package kz.balaguide.common_module.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kz.balaguide.common_module.core.enums.Role;
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
@Table(name = "education_centers")
public class EducationCenter extends AbstractEntity implements Comparable<EducationCenter>{
    /**
     * The name of the education center.
     */
    @NotNull(message = "Name must be not null")
    @NotBlank(message = "Name must be not empty")
    private String name;

    /**
     * The contact phone number of the education center.
     */
    @NotNull(message = "Phone number must be not null")
    @NotBlank(message = "Phone number must be not empty")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email must be valid")
    @Column(unique = true)
    private String email;

    /**
     * The physical address of the education center.
     */
    @NotNull(message = "Address must be not null")
    @NotBlank(message = "Address must be not empty")
    private String address;

    /**
     * The Instagram link for the education center's social media page.
     */
    @NotNull(message = "Instagram link must be not null")
    @NotBlank(message = "Instagram link must be not empty")
    @Column(name = "instagram_link")
    private String instagramLink;

    /**
     * Дата когда образовательный центр открылся
     * */
    @Column(name = "date_of_created")
    private LocalDate dateOfCreated;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * The Balance of Education center
     * */
    @NotNull(message = "Balance must not be null")
    @PositiveOrZero(message = "Balance must be greater than zero or equal to zero")
    private BigDecimal balance;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false, unique = true)
    @ToString.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AuthUser authUser;

    /**
     * The list of courses offered by the education center.
     */
    @OneToMany(mappedBy = "educationCenter")
    @ToString.Exclude
    @JsonIgnore
    private List<Course> courses;

    @Override
    public int compareTo(EducationCenter o) {
        return getId().compareTo(o.getId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        EducationCenter that = (EducationCenter) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

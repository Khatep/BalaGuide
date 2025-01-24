package org.khatep.balaguide.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.khatep.balaguide.models.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "education_center")
public class EducationCenter implements UserDetails {

    /**
     * The unique identifier for the education center.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the education center.
     */
    @NotNull(message = "Name must be not null")
    @NotBlank(message = "Name must be not empty")
    private String name;

    /**
     * The date when the education center was created.
     */
    @NotNull(message = "Date of created must be not null")
    @Past(message = "Date of created must be in the past")
    private LocalDate dateOfCreated;
    
    /**
     * The contact phone number of the education center.
     */
    @NotNull(message = "Phone number must be not null")
    @NotBlank(message = "Phone number must be not empty")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
    @Column(unique = true)
    private String phoneNumber;

    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email must be valid")
    @Column(unique = true)
    private String email;

    /** The password for the education center account. */
    @NotNull(message = "Password must be not null")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

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
    private String instagramLink;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * The Balance of Education center
     * */
    @NotNull(message = "Balance must not be null")
    @PositiveOrZero(message = "Balance must be greater than zero or equal to zero")
    private BigDecimal balance;

    /**
     * The list of courses offered by the education center.
     */
    @OneToMany(mappedBy = "educationCenter")
    @ToString.Exclude
    @JsonIgnore
    private List<Course> courses;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

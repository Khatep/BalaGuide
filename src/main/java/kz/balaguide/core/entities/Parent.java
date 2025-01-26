package kz.balaguide.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kz.balaguide.core.enums.Role;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "parent")
public class Parent implements Comparable<Parent>, UserDetails {

    /** The unique identifier for the parent. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The first name of the parent. */
    @NotNull(message = "First name must be not null")
    @NotBlank(message = "First name must be not empty")
    private String firstName;

    /** The last name of the parent. */
    @NotNull(message = "Last name must be not null")
    @NotBlank(message = "Last name must be not empty")
    private String lastName;

    /** The phone number of the parent. */
    @NotNull(message = "Phone number must be not null")
    @NotBlank(message = "Phone number must be not empty")
    @Column(unique = true)
    private String phoneNumber;

    /**
     * The date of birth of the parent.
     */
    @NotNull(message = "Birth date must be not null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    /** The email address of the parent. */
    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email must be in format: user@example.com")
    @Column(unique = true)
    private String email;

    /** The password for the parent account. */
    @NotNull(message = "Password must be not null")
    @NotBlank(message = "Password must be not empty")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @JsonIgnore
    private String password;

    /** The physical address of the parent. */
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    /** The cash balance of parent, to be removed after integration with online payment. */
    @NotNull(message = "Balance must not be null")
    @PositiveOrZero(message = "Balance must be greater than zero")
    private BigDecimal balance;

    /** A list of children associated with the parent. */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Child> myChildren;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public int compareTo(Parent o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Parent parent = (Parent) o;
        return getId() != null && Objects.equals(getId(), parent.getId());
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
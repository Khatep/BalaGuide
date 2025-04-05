package kz.balaguide.parent_module.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.enums.Role;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParentRequest {

    /** The first name of the parent. */
    @NotNull(message = "First name must be not null")
    @NotBlank(message = "First name must be not empty")
    @Column(name = "first_name")
    private String firstName;

    /** The last name of the parent. */
    @NotNull(message = "Last name must be not null")
    @NotBlank(message = "Last name must be not empty")
    @Column(name = "last_name")
    private String lastName;

    /** The phone number of the parent. */
    @NotNull(message = "Phone number must be not null")
    @NotBlank(message = "Phone number must be not empty")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    /**
     * The date of birth of the parent.
     */
    @NotNull(message = "Birth date must be not null")
    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /** The email address of the parent. */
    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email must be in format: user@example.com")
    @Column(unique = true)
    private String email;

    /** The physical address of the parent. */
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    /** The cash balance of parent, to be removed after integration with online payment. */
    @NotNull(message = "Balance must not be null")
    @PositiveOrZero(message = "Balance must be greater than zero")
    private BigDecimal balance;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false, unique = true)
    @ToString.Exclude
    private AuthUser authUser;

    /** A list of children associated with the parent. */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Child> myChildren;

    @Enumerated(EnumType.STRING)
    private Role role;

}

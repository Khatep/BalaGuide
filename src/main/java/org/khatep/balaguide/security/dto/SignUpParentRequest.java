package org.khatep.balaguide.security.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpParentRequest {

    @NotNull(message = "First name must be not null")
    @NotBlank(message = "First name must be not empty")
    private String firstName;

    @NotNull(message = "Last name must be not null")
    @NotBlank(message = "Last name must be not empty")
    private String lastName;

    @NotNull(message = "Phone number must be not null")
    @NotBlank(message = "Phone number must be not empty")
    private String phoneNumber;

    @NotNull(message = "Birth date must be not null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must not empty")
    @Email(message = "Email must be in format: user@example.com")
    private String email;

    @NotNull(message = "Password must be not null")
    @NotBlank(message = "Password must be not empty")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;
}

package org.khatep.balaguide.security.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.khatep.balaguide.models.enums.Role;

@Data
public class SignInRequest {

    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must not empty")
    @Email(message = "Email must be in format: user@example.com")
    private String email;

    @NotNull(message = "Password must be not null")
    @NotBlank(message = "Password must be not empty")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @NotNull(message = "Role must be not null")
    @NotBlank(message = "Role must be not null")
    @Enumerated(EnumType.STRING)
    private Role role;
}
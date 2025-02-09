package kz.balaguide.core.dtos.auth;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kz.balaguide.core.enums.Role;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record SignUpUserRequest(

        @NotNull(message = "Phone number must be not null")
        @NotBlank(message = "Phone number must be not empty")
        @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
        String phoneNumber,

        @NotNull(message = "Password must be not null")
        @NotBlank(message = "Password must be not empty")
        @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
        String password,

        @Enumerated(EnumType.STRING)
        Role role
) implements Serializable {}

package kz.balaguide.core.dtos.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
public record CreateParentRequest(

        @NotNull(message = "First name must be not null")
        @NotBlank(message = "First name must be not empty")
        String firstName,

        @NotNull(message = "Last name must be not null")
        @NotBlank(message = "Last name must be not empty")
        String lastName,

        @NotNull(message = "Phone number must be not null")
        @NotBlank(message = "Phone number must be not empty")
        String phoneNumber,

        @NotNull(message = "Birth date must be not null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotNull(message = "Email must be not null")
        @NotBlank(message = "Email must not empty")
        @Email(message = "Email must be in format: user@example.com")
        String email

) {}

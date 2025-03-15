package kz.balaguide.parent_module.dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;

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
        @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
        String phoneNumber,

        @NotNull(message = "Birth date must be not null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotNull(message = "Email must be not null")
        @NotBlank(message = "Email must not empty")
        @Email(message = "Email must be in format: user@example.com")
        String email

) {}

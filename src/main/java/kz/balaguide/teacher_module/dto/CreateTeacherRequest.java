package kz.balaguide.teacher_module.dto;

import jakarta.validation.constraints.*;
import kz.balaguide.common_module.core.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTeacherRequest(
        @NotNull(message = "First name must be not null")
        @NotBlank(message = "First name must be not empty")
        String firstName,

        @NotNull(message = "Last name must be not null")
        @NotBlank(message = "Last name must be not empty")
        String lastName,

        @NotNull(message = "Birth date must be not null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotNull(message = "Phone number must be not null")
        @NotBlank(message = "Phone number must be not empty")
        @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
        String phoneNumber,

        @NotNull(message = "Email must be not null")
        @NotBlank(message = "Email must be not empty")
        @Email(message = "Email must be in format: user@example.com")
        String email,


        @NotNull(message = "Salary must be not null")
        @Positive(message = "Salary must be greater than zero")
        BigDecimal salary,

        @NotNull(message = "Gender must not be null")
        Gender gender,

        @NotNull(message = "Education center id must not be null")
        Long educationCenterId
) {}

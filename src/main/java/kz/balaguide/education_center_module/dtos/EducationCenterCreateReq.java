package kz.balaguide.education_center_module.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EducationCenterCreateReq(
        @NotNull(message = "Name must be not null")
        @NotBlank(message = "Name must be not empty")
        String name,

        @NotNull(message = "Date of created must be not null")
        @Past(message = "Date of created must be in the past")
        LocalDate dateOfCreated,

        @NotNull(message = "Phone number must be not null")
        @NotBlank(message = "Phone number must be not empty")
        @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone number must be valid and contain 10-15 digits")
        String phoneNumber,

        @NotNull(message = "Email must be not null")
        @NotBlank(message = "Email must be not empty")
        @Email(message = "Email must be valid")
        String email,

        @NotNull(message = "Address must be not null")
        @NotBlank(message = "Address must be not empty")
        String address,

        @NotNull(message = "Instagram link must be not null")
        @NotBlank(message = "Instagram link must be not empty")
        String instagramLink
) {}


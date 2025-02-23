package kz.balaguide.auth_module.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignInUserRequest(
        @NotNull(message = "Phone number must be not null")
        @NotBlank(message = "Phone number must not empty")
        @Size(min = 8, max = 14, message = "Phone number must be between 8 and 14 characters")
        String phoneNumber,

        @NotNull(message = "Password must be not null")
        @NotBlank(message = "Password must be not empty")
        @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
        String password
) {}
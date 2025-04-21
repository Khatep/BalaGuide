package kz.balaguide.parent_module.dtos;

import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.enums.Gender;

import java.time.LocalDate;

public record CreateChildResponse(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        LocalDate birthDate,
        AuthUser authUser,
        Gender gender
){}

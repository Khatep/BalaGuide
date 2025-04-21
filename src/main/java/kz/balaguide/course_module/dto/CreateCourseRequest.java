package kz.balaguide.course_module.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kz.balaguide.common_module.core.enums.CourseCategory;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateCourseRequest(
        @NotNull(message = "Education center id must be not null")
        Long educationCenterId,

        @NotNull(message = "Name must be not null")
        @NotBlank(message = "Name must be not empty")
        String name,

        @NotNull(message = "Description must be not null")
        @NotBlank(message = "Description must be not empty")
        String description,

        @NotNull(message = "CourseCategory must be not null")
        @Enumerated(EnumType.STRING)
        CourseCategory courseCategory,

        @NotNull(message = "Age range must be not null")
        String ageRange,

        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Durability must not be null")
        @Positive(message = "Durability must be greater than zero")
        Integer durability
) {}

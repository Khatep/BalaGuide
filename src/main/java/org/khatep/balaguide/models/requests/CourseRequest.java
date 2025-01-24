package org.khatep.balaguide.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.khatep.balaguide.models.enums.Category;

import java.math.BigDecimal;

@Builder
public record CourseRequest(
        @NotNull(message = "Education center id must be not null")
        Long educationCenterId,

        @NotNull(message = "Name must be not null")
        @NotBlank(message = "Name must be not empty")
        String name,

        @NotNull(message = "Description must be not null")
        @NotBlank(message = "Description must be not empty")
        String description,

        @NotNull(message = "Category must be not null")
        Category category,

        @NotNull(message = "Age range must be not null")
        String ageRange,

        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Durability must not be null")
        @Positive(message = "Durability must be greater than zero")
        Integer durability,

        @NotNull(message = "Max participants must not be null")
        @Positive(message = "Max participants must be greater than zero")
        int maxParticipants,

        @NotNull(message = "Current participants must not be null")
        int currentParticipants
) {}

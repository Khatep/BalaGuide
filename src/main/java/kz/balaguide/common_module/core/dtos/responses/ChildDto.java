package kz.balaguide.common_module.core.dtos.responses;


import lombok.Builder;

@Builder
public record ChildDto(
        Long id,
        String firstName,
        String lastName
) {}

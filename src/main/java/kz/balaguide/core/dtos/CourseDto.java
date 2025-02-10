package kz.balaguide.core.dtos;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for representing course information.
 *
 * <p>The {@code CourseDto} record encapsulates the details of a course, including
 * its name and price. This record is used to transfer course data between
 * different layers of the application.
 */
@Builder
public record CourseDto(String name, BigDecimal price) {

}

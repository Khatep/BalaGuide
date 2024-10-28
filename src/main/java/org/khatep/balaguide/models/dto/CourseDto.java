package org.khatep.balaguide.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for representing course information.
 *
 * <p>The {@code CourseDto} class encapsulates the details of a course, including
 * its name and price. This class is used to transfer course data between
 * different layers of the application.
 */
@Getter
@Setter
@AllArgsConstructor
public class CourseDto {
    private String name;
    private BigDecimal price;
}

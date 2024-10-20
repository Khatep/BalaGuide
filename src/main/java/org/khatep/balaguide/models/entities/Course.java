package org.khatep.balaguide.models.entities;

import lombok.*;
import org.khatep.balaguide.models.enums.Category;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course implements Comparable<Course> {

    /**
     * The unique identifier of the course.
     */
    private Long id;

    /**
     * The name of the course.
     */
    private String name;

    /**
     * A brief description of the course.
     */
    private String description;

    /**
     * The id of the educational center providing the course.
     */
    private Long educationCenterId;

    /**
     * The category of the course: Programming, Sport, Languages, Art, Math
     */
    private Category category;

    /**
     * The age range suitable for the course (e.g., "6-10 years").
     */
    private String ageRange;

    /**
     * The price of the course.
     */
    private BigDecimal price;

    /**
     * The number of weeks of course duration.
     * */
    private Integer durability;
    
    /**
     * The physical address of the course.
     */
    private String address;

    /**
     * The maximum number of participants for the course.
     */
    private int maxParticipants;
    
    /**
     * The current number of participants in the course.
     */
    private int currentParticipants;

    /**
     * The list of links of additional online materials.
     * */
    private List<String> courseMaterials;

    @Override
    public int compareTo(Course o) {
        return this.getName().compareTo(o.getName());
    }
}

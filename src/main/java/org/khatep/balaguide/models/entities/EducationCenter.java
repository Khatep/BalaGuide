package org.khatep.balaguide.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationCenter {

    /**
     * The unique identifier for the education center.
     */
    private Long id;

    /**
     * The name of the education center.
     */
    private String name;

    /**
     * The date when the education center was created.
     */
    private LocalDate dateOfCreated;

    /**
     * The contact phone number of the education center.
     */
    private String phoneNumber;

    /**
     * The physical address of the education center.
     */
    private String address;

    /**
     * The Instagram link for the education center's social media page.
     */
    private String instagramLink;

    /**
     * The list of courses offered by the education center.
     */
    private List<Course> courses;
}

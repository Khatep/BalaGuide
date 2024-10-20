package org.khatep.balaguide.models.entities;

import lombok.*;
import org.khatep.balaguide.models.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Child implements Comparable<Child> {

    /** The unique identifier for the child. */
    private Long id;

    /** The first name of the child. */
    private String firstName;

    /** The last name of the child. */
    private String lastName;

    /** The birthdate of the child. */
    private LocalDate birthDate;

    /** The phone number of the child. */
    private String phoneNumber;

    /** The password for the child account. */
    private String password;

    /** The gender of the child. */
    private Gender gender;

    /** The id of parent associated with the child. */
    private Long parentId;

    /** A list of courses the child is enrolled in. */
    private List<Course> coursesEnrolled;

    @Override
    public int compareTo(Child o) {
        return this.getId().compareTo(o.getId());
    }
}

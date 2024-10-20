package org.khatep.balaguide.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.khatep.balaguide.models.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    /**
     * The unique identifier for the teacher.
     */
    private Long id;

    /**
     * The first name of the teacher.
     */
    private String firstName;

    /**
     * The last name of the teacher.
     */
    private String lastName;

    /**
     * The date of birth of the teacher.
     */
    private LocalDate dateOfBirth;

    /**
     * The contact phone number of the teacher.
     */
    private String phoneNumber;

    /**
     * The password for the teacher's account.
     * This should be stored in a secure manner.
     */
    private String password;

    /**
     * The salary of the teacher.
     */
    private BigDecimal salary;

    /**
     * The gender of the teacher.
     */
    private Gender gender;
    
    /**
     * The list of courses taught by the teacher.
     * This list may contain multiple {@link Course} objects.
     */
    private List<Course> myCourses;
}

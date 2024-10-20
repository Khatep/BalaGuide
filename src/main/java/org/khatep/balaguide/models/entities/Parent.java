package org.khatep.balaguide.models.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parent implements Comparable<Parent> {

    /** The unique identifier for the parent. */
    private Long id;

    /** The first name of the parent. */
    private String firstName;

    /** The last name of the parent. */
    private String lastName;

    /** The phone number of the parent. */
    private String phoneNumber;

    /** The email address of the parent. */
    private String email;

    /** The password for the parent account. */
    private String password;

    /** The physical address of the parent. */
    private String address;

    /** The cash balance of parent, to be removed after integration with online payment. */
    private BigDecimal balance;

    /** A list of children associated with the parent. */
    private List<Child> myChildren;

    @Override
    public int compareTo(Parent o) {
        return this.getId().compareTo(o.getId());
    }
}
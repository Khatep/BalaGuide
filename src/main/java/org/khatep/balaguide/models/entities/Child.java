package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Child implements Comparable<Child> {

    /** The unique identifier for the child */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The first name of the child */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** The last name of the child */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** The birth date of the child */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /** The phone number of the child */
    @Column(name = "phone_number")
    private String phoneNumber;

    /** The password for the child account */
    @Column(name = "password", nullable = false)
    private String password;

    /** The gender of the child. */
    @Column(name = "gender")
    private String gender;

    /** The parent associated with the child */
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    /** A list of courses the child is enrolled in */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "child_courses", // Join table name
            joinColumns = @JoinColumn(name = "child_id"), // Child's foreign key
            inverseJoinColumns = @JoinColumn(name = "course_id") // Course's foreign key
    )
    @ToString.Exclude
    private List<Course> coursesEnrolled = new ArrayList<>();

    @Override
    public int compareTo(Child o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Child child = (Child) o;
        return getId() != null && Objects.equals(getId(), child.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

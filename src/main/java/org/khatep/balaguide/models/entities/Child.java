package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.khatep.balaguide.models.enums.Gender;

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
@Table(name = "child")
public class Child implements Comparable<Child> {

    /** The unique identifier for the child. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /** The id of parent associated with the child. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "parent_id", referencedColumnName = "id")
    private Parent parent;

    /** A list of courses the child is enrolled in. */
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "child_course",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
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

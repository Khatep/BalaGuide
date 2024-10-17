package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Parent implements Comparable<Parent> {

    /** The unique identifier for the parent */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The first name of the parent */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** The surname of the parent */
    @Column(name = "surname", nullable = false)
    private String surname;

    /** The phone number of the parent */
    @Column(name = "phone_number")
    private String phoneNumber;

    /** The email address of the parent */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /** The password for the parent account */
    @Column(name = "password", nullable = false)
    private String password;

    /** The physical address of the parent */
    @Column(name = "address")
    private String address;

    /** The cash balance of parent, to be removed after integration with online payment */
    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    /** A list of children associated with the parent  */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Child> myChildren;

    @Override
    public int compareTo(Parent o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Parent parent = (Parent) o;
        return getId() != null && Objects.equals(getId(), parent.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
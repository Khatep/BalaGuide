package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parent")
public class Parent implements Comparable<Parent> {

    /** The unique identifier for the parent. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The first name of the parent. */
    private String firstName;

    /** The last name of the parent. */
    private String lastName;

    /** The phone number of the parent. */
    @Column(unique = true)
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
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
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
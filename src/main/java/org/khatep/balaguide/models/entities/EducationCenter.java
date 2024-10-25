package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "education_center")
public class EducationCenter {

    /**
     * The unique identifier for the education center.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * The Balance of Education center
     * */
    private BigDecimal balance;

    /**
     * The list of courses offered by the education center.
     */
    @OneToMany(mappedBy = "educationCenter")
    @ToString.Exclude
    private List<Course> courses;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        EducationCenter that = (EducationCenter) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

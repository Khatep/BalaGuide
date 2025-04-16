package kz.balaguide.common_module.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kz.balaguide.common_module.core.enums.PaymentMethod;
import kz.balaguide.common_module.core.enums.PaymentStatus;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "receipt")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receipt extends AbstractEntity {
    /**
     * Percentage of VAT applied to the transaction.
     */
    @NotNull(message = "Percent of vat must be not null")
    @Column(name = "percent_of_vat")
    private Integer percentOfVat;

    @Column(name = "payment_number")
    private String paymentNumber;

    /**
     * Payment method used for the transaction.
     */
    @NotNull(message = "Payment method must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;

    /**
     * The child id associated with this receipt.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private Child child;

    /**
     * The course id associated with this receipt.
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Receipt receipt = (Receipt) o;
        return getId() != null && Objects.equals(getId(), receipt.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

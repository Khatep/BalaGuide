package kz.balaguide.common_module.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "receipts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receipt extends AbstractEntity {

    @Column(name = "receipt_number", unique = true)
    private UUID receiptNumber;

    @Column(name = "description")
    private String description;

    //https://balaguide.kz/receipts/rcpt-20250416-00001.pdf
    @Column(name = "file_url")
    private String fileUrl;

    //SYSTEM, EDUCATION_SYSTEM
    @Column(name = "issuer")
    private String issuer;

    /**
     * The payment record associated with this receipt.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

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

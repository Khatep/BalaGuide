package org.khatep.balaguide.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.khatep.balaguide.models.enums.PaymentMethod;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "receipt")
public class Receipt {

    /**
     * Unique identifier for the receipt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID of the parent associated with this receipt.
     */
    private Long parentId;

    /**
     * ID of the course associated with this receipt.
     */
    private Long courseId;

    /**
     * Percentage of VAT applied to the transaction.
     */
    private Integer percentOfVat;

    /**
     * Date when the receipt was created.
     */
    private LocalDate dateOfCreated;

    /**
     * Payment method used for the transaction.
     */
    private PaymentMethod paymentMethod;
}

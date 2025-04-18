package kz.balaguide.common_module.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kz.balaguide.common_module.core.enums.PaymentMethod;
import kz.balaguide.common_module.core.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment extends AbstractEntity {

    /**
     * Родитель, который совершил оплату.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)

    private Parent parent;

    /**
     * Ребенок, за которого произведена оплата.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    /**
     * Курс, за который оплачено.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /**
     * Сумма оплаты.
     */
    @NotNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    /**
     * Дата и время проведения оплаты.
     */
    @NotNull
    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    /**
     * Percentage of VAT applied to the transaction.
     */
    @NotNull(message = "Percent of vat must be not null")
    @Column(name = "percent_of_vat")
    private Integer percentOfVat;


    /**
     * Способ оплаты (например, CARD, BALANCE, CRYPTO и т.д.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    /**
     * Статус оплаты (успешно, неуспешно, в ожидании).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    // При необходимости можно добавить номер транзакции или чек
    @Column(name = "transaction_id")
    private String transactionId;
}

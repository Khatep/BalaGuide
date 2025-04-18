package kz.balaguide.common_module.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_cards")
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_user_id", referencedColumnName = "id" )
    AuthUser authUser;

    @NotNull(message = "Bank card number must not be null")
    @Pattern(regexp = "\\d{16}", message = "Bank card number must be a 16-digit number")
    String pan;

    @Column(name = "card_expired_date")
    @NotNull(message = "Card expiration date must not be null")
    @Pattern(regexp = "(0[1-9]|1[0-2])/(\\d{2})", message = "Card expiration date must be in MM/YY format")
    String cardExpiredDate;

    @NotNull(message = "CVV must not be null")
    @Pattern(regexp = "\\d{3}", message = "CVV must be a 3-digit number")
    String cvv;

    @Column(name = "holder_name")
    String holderName;
}
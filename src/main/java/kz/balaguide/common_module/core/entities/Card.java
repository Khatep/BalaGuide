package kz.balaguide.common_module.core.entities;

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
public class Card {
    @NotNull(message = "Bank card number must not be null")
    @Pattern(regexp = "\\d{16}", message = "Bank card number must be a 16-digit number")
    String pan;

    @NotNull(message = "Card expiration date must not be null")
    @Pattern(regexp = "(0[1-9]|1[0-2])/(\\d{2})", message = "Card expiration date must be in MM/YY format")
    String cardExpiredDate;

    @NotNull(message = "CVV must not be null")
    @Pattern(regexp = "\\d{3}", message = "CVV must be a 3-digit number")
    String cvv;
}
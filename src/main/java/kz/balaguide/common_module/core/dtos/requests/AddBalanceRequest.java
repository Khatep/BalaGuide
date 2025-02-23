package kz.balaguide.common_module.core.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record AddBalanceRequest(
        @NotNull(message = "Amount of money must not be null")
        @Positive(message = "Amount of money must be greater than zero")
        Integer amountOfMoney,

        @NotNull(message = "Bank card number must not be null")
        @Pattern(regexp = "\\d{16}", message = "Bank card number must be a 16-digit number")
        String numberOfBankCard
) {}

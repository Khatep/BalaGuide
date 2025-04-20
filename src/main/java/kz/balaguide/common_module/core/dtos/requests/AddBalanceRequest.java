package kz.balaguide.common_module.core.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kz.balaguide.common_module.core.entities.BankCard;
import lombok.*;

@Builder
public record AddBalanceRequest(
        @NotNull(message = "Amount of money must not be null")
        @Positive(message = "Amount of money must be greater than zero")
        Integer amountOfMoney,

        @NotNull(message = "Card must not be null")
        BankCard bankCard
) {}

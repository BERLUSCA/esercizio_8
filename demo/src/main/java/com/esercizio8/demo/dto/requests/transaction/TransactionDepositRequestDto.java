package com.esercizio8.demo.dto.requests.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionDepositRequestDto {
    @NotNull(message = "enter the amount")
    private float balance;
}

package com.esercizio8.demo.Dto.Responses.Transaction;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class TransactionDepositResponseDto {

    @AllArgsConstructor
    @Getter
    public enum TransactionType {
        WITHDRAWAL,
        DEPOSIT
    }

    @NotNull
    private float balance;

    @NotNull
    private TransactionType type;

    @NotNull
    private LocalDateTime createdAt;

}

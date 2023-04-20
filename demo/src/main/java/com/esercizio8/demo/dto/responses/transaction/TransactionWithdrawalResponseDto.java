package com.esercizio8.demo.dto.responses.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionWithdrawalResponseDto {

    @NotNull
    private float balance;

    @NotNull
    private LocalDateTime createdAt;

}

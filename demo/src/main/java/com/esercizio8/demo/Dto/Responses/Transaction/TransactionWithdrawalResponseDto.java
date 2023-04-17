package com.esercizio8.demo.Dto.Responses.Transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionWithdrawalResponseDto {

    @NotNull
    private float balance;

    @NotNull
    private LocalDateTime createdAt;

}

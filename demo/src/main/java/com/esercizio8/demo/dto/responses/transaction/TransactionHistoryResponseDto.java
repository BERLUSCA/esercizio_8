package com.esercizio8.demo.dto.responses.transaction;

import com.esercizio8.demo.model.Transaction;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionHistoryResponseDto {

    private UUID id;
    private float balance;
    private Transaction.Type type;
    private LocalDateTime createdAt;
}

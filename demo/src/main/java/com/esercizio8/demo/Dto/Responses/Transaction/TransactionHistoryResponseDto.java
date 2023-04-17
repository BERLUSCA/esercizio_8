package com.esercizio8.demo.Dto.Responses.Transaction;

import com.esercizio8.demo.Model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionHistoryResponseDto {

    private UUID id;
    private float balance;
    private Transaction.TransactionType type;
    private LocalDateTime createdAt;
}

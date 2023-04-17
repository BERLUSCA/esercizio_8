package com.esercizio8.demo.Dto.Requests.Transaction;

import com.esercizio8.demo.Model.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class TransactionDepositRequestDto {
    @NotNull(message = "insert the import")
    private float balance;
}

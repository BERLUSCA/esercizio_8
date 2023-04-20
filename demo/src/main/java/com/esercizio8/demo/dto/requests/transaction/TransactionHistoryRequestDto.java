package com.esercizio8.demo.dto.requests.transaction;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class TransactionHistoryRequestDto {
    @NotNull
    private UUID id_card;
    @NotNull
    private UUID id_user;
}

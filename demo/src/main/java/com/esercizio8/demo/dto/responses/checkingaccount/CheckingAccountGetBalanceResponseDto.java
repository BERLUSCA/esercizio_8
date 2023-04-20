package com.esercizio8.demo.dto.responses.checkingaccount;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckingAccountGetBalanceResponseDto {

    @NotNull
    public float balance;

    public CheckingAccountGetBalanceResponseDto(float balance) {
        this.balance = balance;
    }
}

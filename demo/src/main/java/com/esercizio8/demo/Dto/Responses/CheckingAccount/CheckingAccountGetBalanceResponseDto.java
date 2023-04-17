package com.esercizio8.demo.Dto.Responses.CheckingAccount;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckingAccountGetBalanceResponseDto {

    @NotNull
    public float balance;
}

package com.esercizio8.demo.Dto.Responses.CheckingAccount;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckingAccountUpdateResponseDto {

    @NotNull
    private UUID id;

    @NotNull
    @Size(max = 27)
    private String iban;

    @NotNull
    private float balance;

    @NotNull
    private int pin;

}

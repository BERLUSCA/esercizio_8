package com.esercizio8.demo.dto.requests.transaction;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TransactionWithdrawalRequestDto {

    @NotNull
    private float balance;

    @NotNull(message = "please insert the pin")
    @Digits(integer = 5, fraction = 0, message = "the pin must be 5 digits long")
    @Min(value = 10000)
    @Max(value = 99999)
    private int pin;

}

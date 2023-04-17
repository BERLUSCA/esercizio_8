package com.esercizio8.demo.Dto.Responses.CheckingAccount;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CheckingAccountCreateResponseDto {

    @NotNull
    private UUID id;

    @NotNull
    @Size(max = 27)
    private String iban;

    @NotNull
    private float balance;

    @NotNull
    private LocalDateTime valid_thru;

    @Digits(integer = 5, fraction = 0, message = "the pin must be 5 digits long")
    @Min(value = 10000)
    @Max(value = 99999)
    private int pin;

}

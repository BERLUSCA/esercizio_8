package com.esercizio8.demo.Dto.Requests.CheckingAccount;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CheckingAccountCreateRequestDto {

    @NotNull(message = "iban required")
    @Size(max = 27)
    private String iban;

    private float balance;

    @Digits(integer = 5, fraction = 0, message = "the pin must be 5 digits long")
    @Min(value = 10000)
    @Max(value = 99999)
    private int pin;

    @NotNull(message = "valid_thru required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate valid_thru;
    @NotNull
    private UUID id_user;
}

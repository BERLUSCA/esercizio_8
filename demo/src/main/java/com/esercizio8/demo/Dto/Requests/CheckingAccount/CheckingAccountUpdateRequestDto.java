package com.esercizio8.demo.Dto.Requests.CheckingAccount;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class CheckingAccountUpdateRequestDto {

    @NotNull
    @Size(max = 27)
    private String iban;

    @NotNull
    private float balance;

    @NotNull
    @Digits(integer = 5, fraction = 0, message = "the pin must be 5 digits long")
    @Min(value = 10000)
    @Max(value = 99999)
    private int pin;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate valid_thru;

}

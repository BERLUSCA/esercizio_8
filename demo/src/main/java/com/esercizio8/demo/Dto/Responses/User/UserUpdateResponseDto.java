package com.esercizio8.demo.Dto.Responses.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateResponseDto {

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String email;

}

package com.esercizio8.demo.dto.responses.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserUpdateResponseDto {

    @NotNull
    private UUID id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String email;

}

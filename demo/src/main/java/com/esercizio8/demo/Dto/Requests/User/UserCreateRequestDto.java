package com.esercizio8.demo.Dto.Requests.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequestDto {
    @NotNull
    @Size(max = 75, message = "firstname not valid, max length is 75")
    private String firstname;

    @NotNull
    @Size(max = 75, message = "lastname not valid, max length is 75")
    private String lastname;

    @NotNull
    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b",
             message = "Email not valid")
    private String email;
}

package com.esercizio8.demo.model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "'user'")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NotNull
    @Column(name = "firstname", length = 75)
    private String firstName;
    @NotNull
    @Column(name = "lastname", length = 75)
    private String lastName;
    @NotNull
    @Column(name = "email")
    @Email(regexp = "^\\S+@\\S+\\.\\S+$", message = "Email not valid")
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CheckingAccount> checkingAccounts;
    public CheckingAccount assignCheckingAccount(CheckingAccount checkingAccount){
        getCheckingAccounts().add(checkingAccount);
        checkingAccount.setUser(this);
        return checkingAccount;
    }
}
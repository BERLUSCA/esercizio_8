package com.esercizio8.demo.Model;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "checking_account")
public class CheckingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Column(name = "iban", length = 27)
    private String iban;

    @NotNull
    @Column(name = "pin", length = 5)
    private int pin;

    @Column(name = "balance", columnDefinition = "REAL default '0'")
    private float balance;


    @Column(name = "valid_thru")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate validThru;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "checkingAccount", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Transaction> transactions;
}
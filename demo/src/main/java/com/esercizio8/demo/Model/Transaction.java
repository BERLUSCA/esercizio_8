package com.esercizio8.demo.Model;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "transaction")
public class Transaction {

    @AllArgsConstructor
    @Getter
    public enum TransactionType {
        WITHDRAWAL,
        DEPOSIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "balance")
    private float balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "checking_account_id", referencedColumnName = "id")
    private CheckingAccount checkingAccount;

}

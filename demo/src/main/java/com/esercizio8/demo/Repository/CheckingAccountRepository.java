package com.esercizio8.demo.Repository;

import com.esercizio8.demo.Model.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, UUID> {
    boolean existsByIban(String iban);
}

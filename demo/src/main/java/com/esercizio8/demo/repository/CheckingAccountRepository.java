package com.esercizio8.demo.repository;

import com.esercizio8.demo.model.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, UUID> {
    boolean existsByIban(String iban);
}

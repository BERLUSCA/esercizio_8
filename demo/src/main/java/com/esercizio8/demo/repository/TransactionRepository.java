package com.esercizio8.demo.repository;
import com.esercizio8.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByCheckingAccount_IdOrderByCreatedAtDesc(UUID checkingAccountId, Pageable pageable);
}

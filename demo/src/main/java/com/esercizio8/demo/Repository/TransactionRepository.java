package com.esercizio8.demo.Repository;

import com.esercizio8.demo.Model.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "createdAt");
    List<Transaction> findAllByCheckingAccount_IdOrderByCreatedAtDesc(UUID checkingAccountId, Pageable pageable);

}

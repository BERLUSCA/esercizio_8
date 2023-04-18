package com.esercizio8.demo.Helper;

import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Model.CheckingAccount;
import com.esercizio8.demo.Model.Transaction;
import com.esercizio8.demo.Repository.CheckingAccountRepository;
import com.esercizio8.demo.Repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TransactionHelper {

    CheckingAccountRepository checkingAccountRepository;
    TransactionRepository transactionRepository;

    public TransactionHelper(TransactionRepository transactionRepository,
                             CheckingAccountRepository checkingAccountRepository) {
        this.checkingAccountRepository = checkingAccountRepository;
        this.transactionRepository = transactionRepository;
    }


    public CheckingAccount checkCheckingAccountExists(UUID id) {
        return checkingAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckingAccount with id " + id + " not found"));
    }

    public Transaction createTransaction(Transaction.TransactionType type, float balance, CheckingAccount checkingAccount) {
        Transaction transaction = new Transaction();
        transaction.setType(type);
        transaction.setBalance(balance);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCheckingAccount(checkingAccount);
        return transaction;
    }
}

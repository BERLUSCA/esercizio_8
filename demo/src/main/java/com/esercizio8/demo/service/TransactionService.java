package com.esercizio8.demo.service;

import com.esercizio8.demo.dto.requests.transaction.TransactionDepositRequestDto;
import com.esercizio8.demo.dto.requests.transaction.TransactionWithdrawalRequestDto;
import com.esercizio8.demo.dto.responses.transaction.TransactionDepositResponseDto;
import com.esercizio8.demo.dto.responses.transaction.TransactionHistoryResponseDto;
import com.esercizio8.demo.dto.responses.transaction.TransactionWithdrawalResponseDto;
import com.esercizio8.demo.exception.ResourceNotFoundException;
import com.esercizio8.demo.helper.CheckingAccountHelper;
import com.esercizio8.demo.model.CheckingAccount;
import com.esercizio8.demo.model.Transaction;
import com.esercizio8.demo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ModelMapper modelMapper;
    private final TransactionRepository transactionRepository;
    private final CheckingAccountHelper checkingAccountHelper;

    private Transaction createTransaction(Transaction.Type type, float balance, CheckingAccount checkingAccount) {
        Transaction transaction = new Transaction();
        transaction.setType(type);
        transaction.setBalance(balance);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCheckingAccount(checkingAccount);
        return transaction;
    }

    public TransactionDepositResponseDto makeDepositById(TransactionDepositRequestDto transactionDeposit, UUID id) {
        CheckingAccount checkingAccount = checkingAccountHelper.getCheckingAccount(id);
        checkingAccount.setBalance(checkingAccount.getBalance() + transactionDeposit.getBalance());
        Transaction transaction = createTransaction(Transaction.Type.DEPOSIT, transactionDeposit.getBalance(), checkingAccount);
        transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionDepositResponseDto.class);
    }

    public TransactionWithdrawalResponseDto makeWithdrawalById(TransactionWithdrawalRequestDto transactionWithdrawal, UUID id) {
        CheckingAccount checkingAccount = checkingAccountHelper.getCheckingAccount(id);

        if(checkingAccount.getPin() != transactionWithdrawal.getPin()){
            throw new ResourceNotFoundException("Pin is not correct");
        }

        if(transactionWithdrawal.getBalance() > checkingAccount.getBalance()) {
            throw new ResourceNotFoundException("Balance not available, your balance is: " + checkingAccount.getBalance());
        }

        checkingAccount.setBalance(checkingAccount.getBalance() - transactionWithdrawal.getBalance());
        Transaction transaction = createTransaction(Transaction.Type.WITHDRAWAL, transactionWithdrawal.getBalance(), checkingAccount);
        transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionWithdrawalResponseDto.class);
    }

    public List<TransactionHistoryResponseDto> getTransactionHistory(UUID idCard,
                                                                     int page,
                                                                     int size) {
        List<Transaction> transactionHistory = transactionRepository
                                               .findAllByCheckingAccount_IdOrderByCreatedAtDesc(idCard, PageRequest.of(page, size, Sort.Direction.DESC, "createdAt"));

        if (transactionHistory.isEmpty()) {
            throw new ResourceNotFoundException("Transaction history not available");
        }

        return transactionHistory.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionHistoryResponseDto.class))
                .collect(Collectors.toList());
    }
}

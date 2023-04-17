package com.esercizio8.demo.Service;

import com.esercizio8.demo.Dto.Requests.Transaction.TransactionDepositRequestDto;
import com.esercizio8.demo.Dto.Requests.Transaction.TransactionHistoryRequestDto;
import com.esercizio8.demo.Dto.Requests.Transaction.TransactionWithdrawalRequestDto;
import com.esercizio8.demo.Dto.Responses.Transaction.TransactionDepositResponseDto;
import com.esercizio8.demo.Dto.Responses.Transaction.TransactionHistoryResponseDto;
import com.esercizio8.demo.Dto.Responses.Transaction.TransactionWithdrawalResponseDto;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Model.CheckingAccount;
import com.esercizio8.demo.Model.Transaction;
import com.esercizio8.demo.Repository.CheckingAccountRepository;
import com.esercizio8.demo.Repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private ModelMapper modelMapper;

    private final TransactionRepository transactionRepository;
    private final CheckingAccountRepository checkingAccountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              CheckingAccountRepository checkingAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.checkingAccountRepository = checkingAccountRepository;
    }


    public TransactionDepositResponseDto makeDepositById(TransactionDepositRequestDto transactionDeposit, UUID id) {
        CheckingAccount checkingAccount = checkingAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckingAccount with id " + id + " not found"));

        checkingAccount.setBalance(checkingAccount.getBalance() + transactionDeposit.getBalance());

        Transaction transaction = new Transaction();
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setBalance(transactionDeposit.getBalance());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCheckingAccount(checkingAccount);
        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionDepositResponseDto.class);

    }

    public TransactionWithdrawalResponseDto makeWithdrawalById(TransactionWithdrawalRequestDto transactionWithdrawal, UUID id) {

        CheckingAccount checkingAccount = checkingAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckingAccount with id " + id + " not found"));

        if(transactionWithdrawal.getBalance() > checkingAccount.getBalance()) {
            throw new ResourceNotFoundException("Balance not available, Your balance is: " + checkingAccount.getBalance());
        }
        checkingAccount.setBalance(checkingAccount.getBalance() - transactionWithdrawal.getBalance());

        Transaction transaction = new Transaction();
        transaction.setType(Transaction.TransactionType.WITHDRAWAL);
        transaction.setBalance(transactionWithdrawal.getBalance());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCheckingAccount(checkingAccount);

        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionWithdrawalResponseDto.class);

    }

    public List<TransactionHistoryResponseDto> getTransactionHistory(UUID id_card, int page, int size) {
        List<Transaction> transactionHistory = transactionRepository
                                               .findAllByCheckingAccount_IdOrderByCreatedAtDesc(id_card, PageRequest.of(page, size, Sort.Direction.DESC, "createdAt"));

        if (transactionHistory.isEmpty()) {
            throw new ResourceNotFoundException("Transaction history not found");
        }

        List<TransactionHistoryResponseDto> transactionHistoryDto = transactionHistory.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionHistoryResponseDto.class))
                .collect(Collectors.toList());

        return transactionHistoryDto;
    }
}

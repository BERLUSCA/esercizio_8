package com.esercizio8.demo.Service;

import com.esercizio8.demo.Dto.Requests.Transaction.TransactionDepositRequestDto;
import com.esercizio8.demo.Dto.Requests.Transaction.TransactionHistoryRequestDto;
import com.esercizio8.demo.Dto.Requests.Transaction.TransactionWithdrawalRequestDto;
import com.esercizio8.demo.Dto.Responses.Transaction.TransactionDepositResponseDto;
import com.esercizio8.demo.Dto.Responses.Transaction.TransactionHistoryResponseDto;
import com.esercizio8.demo.Dto.Responses.Transaction.TransactionWithdrawalResponseDto;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Helper.TransactionHelper;
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
    private final TransactionHelper transactionHelper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              CheckingAccountRepository checkingAccountRepository,
                              TransactionHelper transactionHelper) {

        this.transactionRepository = transactionRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.transactionHelper = transactionHelper;
    }


    public TransactionDepositResponseDto makeDepositById(TransactionDepositRequestDto transactionDeposit, UUID id) {
        CheckingAccount checkingAccount = transactionHelper.checkCheckingAccountExists(id);

        checkingAccount.setBalance(checkingAccount.getBalance() + transactionDeposit.getBalance());

        Transaction transaction = transactionHelper.createTransaction(Transaction.TransactionType.DEPOSIT,
                                                                      transactionDeposit.getBalance(), checkingAccount);
        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionDepositResponseDto.class);

    }

    public TransactionWithdrawalResponseDto makeWithdrawalById(TransactionWithdrawalRequestDto transactionWithdrawal, UUID id) {

        CheckingAccount checkingAccount = transactionHelper.checkCheckingAccountExists(id);

        if(!(checkingAccount.getPin() == transactionWithdrawal.getPin())){
            throw new ResourceNotFoundException("Pin is not correct");
        }

        if(transactionWithdrawal.getBalance() > checkingAccount.getBalance()) {
            throw new ResourceNotFoundException("Balance not available, your balance is: " + checkingAccount.getBalance());
        }

        checkingAccount.setBalance(checkingAccount.getBalance() - transactionWithdrawal.getBalance());

        Transaction transaction = transactionHelper.createTransaction(Transaction.TransactionType.WITHDRAWAL,
                transactionWithdrawal.getBalance(), checkingAccount);

        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionWithdrawalResponseDto.class);

    }

    public List<TransactionHistoryResponseDto> getTransactionHistory(UUID id_card,
                                                                     int page,
                                                                     int size) {
        List<Transaction> transactionHistory = transactionRepository
                                               .findAllByCheckingAccount_IdOrderByCreatedAtDesc(id_card, PageRequest.of(page, size, Sort.Direction.DESC, "createdAt"));

        if (transactionHistory.isEmpty()) {
            throw new ResourceNotFoundException("Transaction history not available");
        }

        List<TransactionHistoryResponseDto> transactionHistoryDto = transactionHistory.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionHistoryResponseDto.class))
                .collect(Collectors.toList());

        return transactionHistoryDto;
    }
}

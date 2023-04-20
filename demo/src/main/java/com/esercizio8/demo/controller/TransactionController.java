package com.esercizio8.demo.controller;

import com.esercizio8.demo.dto.requests.transaction.TransactionDepositRequestDto;
import com.esercizio8.demo.dto.requests.transaction.TransactionWithdrawalRequestDto;
import com.esercizio8.demo.dto.responses.transaction.TransactionDepositResponseDto;
import com.esercizio8.demo.dto.responses.transaction.TransactionHistoryResponseDto;
import com.esercizio8.demo.dto.responses.transaction.TransactionWithdrawalResponseDto;
import com.esercizio8.demo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/transaction")
@RestController
public class TransactionController {

    TransactionService transactionService;
    public TransactionController(TransactionService transactionService) { this.transactionService = transactionService; }

    @PostMapping("/deposit/{id}")
    public TransactionDepositResponseDto createDepositTransaction(@Valid @RequestBody TransactionDepositRequestDto transactionDeposit,
                                                                  @PathVariable UUID id) {
        return transactionService.makeDepositById(transactionDeposit, id);
    }

    @PostMapping("/withdrawal/{id}")
    public TransactionWithdrawalResponseDto createWithdrawalTransaction(@Valid @RequestBody TransactionWithdrawalRequestDto transactionWithdrawal,
                                                                        @PathVariable UUID id) {
        return transactionService.makeWithdrawalById(transactionWithdrawal, id);
    }

    @GetMapping("/{id_card}")
    public List<TransactionHistoryResponseDto> getHistoryTransaction(@PathVariable UUID id_card,
                                                                     @RequestParam int page,
                                                                     @RequestParam int size) {
        return transactionService.getTransactionHistory(id_card, page, size);
    }
}
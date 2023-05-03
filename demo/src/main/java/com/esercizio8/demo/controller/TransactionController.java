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

    @PostMapping("/deposit/{idTransaction}")
    public TransactionDepositResponseDto createDepositTransaction(@Valid @RequestBody TransactionDepositRequestDto transactionDeposit,
                                                                  @PathVariable UUID idTransaction) {
        return transactionService.makeDepositById(transactionDeposit, idTransaction);
    }

    @PostMapping("/withdrawal/{idTransaction}")
    public TransactionWithdrawalResponseDto createWithdrawalTransaction(@Valid @RequestBody TransactionWithdrawalRequestDto transactionWithdrawal,
                                                                        @PathVariable UUID idTransaction) {
        return transactionService.makeWithdrawalById(transactionWithdrawal, idTransaction);
    }

    @GetMapping("/{id_card}")
    public List<TransactionHistoryResponseDto> getHistoryTransaction(@PathVariable UUID idCard,
                                                                     @RequestParam int page,
                                                                     @RequestParam int size) {
        return transactionService.getTransactionHistory(idCard, page, size);
    }
}
package com.esercizio8.demo.controller;
import com.esercizio8.demo.dto.requests.checkingaccount.CheckingAccountCreateRequestDto;
import com.esercizio8.demo.dto.responses.checkingaccount.CheckingAccountGetBalanceResponseDto;
import com.esercizio8.demo.dto.requests.checkingaccount.CheckingAccountUpdateRequestDto;
import com.esercizio8.demo.dto.responses.checkingaccount.CheckingAccountCreateResponseDto;
import com.esercizio8.demo.dto.responses.checkingaccount.CheckingAccountUpdateResponseDto;
import com.esercizio8.demo.service.CheckingAccountService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/checkingAccount")
@RestController
public class CheckingAccountController {
    CheckingAccountService checkingAccountService;

    public CheckingAccountController(CheckingAccountService checkingAccountService) {
        this.checkingAccountService = checkingAccountService;
    }
    @PostMapping
    public CheckingAccountCreateResponseDto createCheckingAccount(@Valid @RequestBody CheckingAccountCreateRequestDto checkingAccount) {
        return checkingAccountService.registerCheckingAccount(checkingAccount);
    }

    @PutMapping("/{id}")
    public CheckingAccountUpdateResponseDto updateCheckingAccount(@Valid @RequestBody CheckingAccountUpdateRequestDto checkingAccount,
                                                                  @PathVariable UUID id) {
        return checkingAccountService.updateCheckingAccount(checkingAccount, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCheckingAccount(@PathVariable UUID id) {
        checkingAccountService.deleteCheckingAccountById(id);
    }

    @GetMapping("/{id}/balance")
    public CheckingAccountGetBalanceResponseDto getBalanceById(@PathVariable UUID id) {
        return checkingAccountService.getBalanceById(id);
    }
}

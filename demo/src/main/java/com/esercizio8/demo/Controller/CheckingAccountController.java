package com.esercizio8.demo.Controller;

import com.esercizio8.demo.Dto.Requests.CheckingAccount.CheckingAccountCreateRequestDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountGetBalanceResponseDto;
import com.esercizio8.demo.Dto.Requests.CheckingAccount.CheckingAccountUpdateRequestDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountCreateResponseDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountUpdateResponseDto;
import com.esercizio8.demo.Service.CheckingAccountService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/checkingAccount")
@Validated
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

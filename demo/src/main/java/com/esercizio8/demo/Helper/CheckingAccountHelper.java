package com.esercizio8.demo.Helper;

import com.esercizio8.demo.Exception.EmailAlreadyExistsException;
import com.esercizio8.demo.Exception.IbanAlreadyExistsException;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Model.CheckingAccount;
import com.esercizio8.demo.Model.User;
import com.esercizio8.demo.Repository.CheckingAccountRepository;
import com.esercizio8.demo.Repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CheckingAccountHelper {

    private CheckingAccountRepository checkingAccountRepository;

    public CheckingAccountHelper(CheckingAccountRepository checkingAccountRepository) {
        this.checkingAccountRepository = checkingAccountRepository;
    }

    public void checkIbanAlreadyExists(String iban) {
        if(checkingAccountRepository.existsByIban(iban)){
            throw new IbanAlreadyExistsException("Iban is already assigned");
        }
    }

    public CheckingAccount checkCheckingAccountExists(UUID id) {
        return checkingAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckingAccount with id " + id + " not found."));
    }

}
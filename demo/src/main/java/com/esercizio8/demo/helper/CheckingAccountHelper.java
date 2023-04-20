package com.esercizio8.demo.helper;
import com.esercizio8.demo.exception.IbanAlreadyExistsException;
import com.esercizio8.demo.exception.ResourceNotFoundException;
import com.esercizio8.demo.model.CheckingAccount;
import com.esercizio8.demo.repository.CheckingAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CheckingAccountHelper {

    private final CheckingAccountRepository checkingAccountRepository;


    public void checkIbanAlreadyExists(String iban) {
        if(checkingAccountRepository.existsByIban(iban)){
            throw new IbanAlreadyExistsException("Iban is already assigned");
        }
    }

    public CheckingAccount getCheckingAccount(UUID id) {
        return checkingAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckingAccount with id " + id + " not found."));
    }

}
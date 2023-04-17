package com.esercizio8.demo.Service;

import com.esercizio8.demo.Dto.Requests.CheckingAccount.CheckingAccountCreateRequestDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountGetBalanceResponseDto;
import com.esercizio8.demo.Dto.Requests.CheckingAccount.CheckingAccountUpdateRequestDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountCreateResponseDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountUpdateResponseDto;
import com.esercizio8.demo.Exception.CardExpireException;
import com.esercizio8.demo.Exception.IbanAlreadyExistsException;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Model.CheckingAccount;
import com.esercizio8.demo.Model.User;
import com.esercizio8.demo.Repository.CheckingAccountRepository;
import com.esercizio8.demo.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CheckingAccountService {

    @Autowired
    private ModelMapper modelMapper;
    CheckingAccountRepository checkingAccountRepository;
    UserRepository userRepository;
    @Autowired
    public CheckingAccountService(CheckingAccountRepository checkingAccountRepository,
                                  UserRepository userRepository) {
        this.checkingAccountRepository = checkingAccountRepository;
        this.userRepository = userRepository;
    }

    public CheckingAccountCreateResponseDto registerCheckingAccount(CheckingAccountCreateRequestDto checkingAccount) {
        if(checkingAccountRepository.existsByIban(checkingAccount.getIban())){
           throw new IbanAlreadyExistsException("Iban is already assigned");
        }
        User user = userRepository.findById(checkingAccount.getId_user())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + checkingAccount.getId_user() + " not found."));

        CheckingAccount checkingAccountSaved = modelMapper.map(checkingAccount, CheckingAccount.class);
        user.assignCheckingAccount(checkingAccountSaved);
        CheckingAccount checkingAccountDto = checkingAccountRepository
                                               .save(checkingAccountSaved);


        return modelMapper.map(checkingAccountDto, CheckingAccountCreateResponseDto.class);
    }

    public CheckingAccountUpdateResponseDto updateCheckingAccount(CheckingAccountUpdateRequestDto updateCheckingAccount, UUID id) {

        CheckingAccount checkingAccount = checkingAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckingAccount with id " + id + " not found."));

        if(!checkingAccount.getIban().equals(updateCheckingAccount.getIban())) {
            if (checkingAccountRepository.existsByIban(updateCheckingAccount.getIban())) {
                throw new IbanAlreadyExistsException("Iban is already assigned");
            }
            checkingAccount.setIban(updateCheckingAccount.getIban());
        }

        checkingAccount.setPin(updateCheckingAccount.getPin());
        checkingAccount.setBalance(updateCheckingAccount.getBalance());
        checkingAccount.setValidThru(updateCheckingAccount.getValid_thru());

        return modelMapper.map(checkingAccountRepository.save(checkingAccount),
                               CheckingAccountUpdateResponseDto.class);
    }

    public void deleteCheckingAccountById(UUID id) { checkingAccountRepository.deleteById(id); }

    public CheckingAccountGetBalanceResponseDto getBalanceById(UUID id) {

        CheckingAccount checkingAccount = checkingAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckingAccount with id " + id + " not found."));

        if(checkingAccount.getValidThru().isBefore(LocalDate.now())) {
            throw new CardExpireException("Card has expired");
        }
        CheckingAccountGetBalanceResponseDto checkingAccountDto = new CheckingAccountGetBalanceResponseDto();

        checkingAccountDto.setBalance(checkingAccount.getBalance());
        return checkingAccountDto;

    }
}

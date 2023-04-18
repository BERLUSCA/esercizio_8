package com.esercizio8.demo.Service;

import com.esercizio8.demo.Dto.Requests.CheckingAccount.CheckingAccountCreateRequestDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountGetBalanceResponseDto;
import com.esercizio8.demo.Dto.Requests.CheckingAccount.CheckingAccountUpdateRequestDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountCreateResponseDto;
import com.esercizio8.demo.Dto.Responses.CheckingAccount.CheckingAccountUpdateResponseDto;
import com.esercizio8.demo.Exception.CardExpireException;
import com.esercizio8.demo.Exception.IbanAlreadyExistsException;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Helper.CheckingAccountHelper;
import com.esercizio8.demo.Helper.UserHelper;
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
    CheckingAccountHelper checkingAccountHelper;
    UserHelper userHelper;
    @Autowired
    public CheckingAccountService(CheckingAccountRepository checkingAccountRepository,
                                  UserRepository userRepository,
                                  CheckingAccountHelper checkingAccountHelper,
                                  UserHelper userHelper) {

        this.checkingAccountRepository = checkingAccountRepository;
        this.userRepository = userRepository;
        this.checkingAccountHelper = checkingAccountHelper;
        this.userHelper = userHelper;
    }

    public CheckingAccountCreateResponseDto registerCheckingAccount(CheckingAccountCreateRequestDto checkingAccount) {
        checkingAccountHelper.checkIbanAlreadyExists(checkingAccount.getIban());
        User user = userHelper.checkUserAlreadyExists(checkingAccount.getId_user());

        CheckingAccount checkingAccountSaved = modelMapper.map(checkingAccount, CheckingAccount.class);
        user.assignCheckingAccount(checkingAccountSaved);

        return modelMapper.map(checkingAccountRepository.save(checkingAccountSaved),
                CheckingAccountCreateResponseDto.class);
    }

    public CheckingAccountUpdateResponseDto updateCheckingAccount(CheckingAccountUpdateRequestDto updateCheckingAccount, UUID id) {

        CheckingAccount checkingAccount = checkingAccountHelper.checkCheckingAccountExists(id);

        if(!checkingAccount.getIban().equals(updateCheckingAccount.getIban())) {
            checkingAccountHelper.checkIbanAlreadyExists(updateCheckingAccount.getIban());
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

        CheckingAccount checkingAccount = checkingAccountHelper.checkCheckingAccountExists(id);

        if(checkingAccount.getValidThru().isBefore(LocalDate.now())) {
            throw new CardExpireException("Card has expired");
        }

        return new CheckingAccountGetBalanceResponseDto(checkingAccount.getBalance());

    }
}

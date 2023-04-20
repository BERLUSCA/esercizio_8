package com.esercizio8.demo.service;
import com.esercizio8.demo.dto.requests.checkingaccount.CheckingAccountCreateRequestDto;
import com.esercizio8.demo.dto.responses.checkingaccount.CheckingAccountGetBalanceResponseDto;
import com.esercizio8.demo.dto.requests.checkingaccount.CheckingAccountUpdateRequestDto;
import com.esercizio8.demo.dto.responses.checkingaccount.CheckingAccountCreateResponseDto;
import com.esercizio8.demo.dto.responses.checkingaccount.CheckingAccountUpdateResponseDto;
import com.esercizio8.demo.exception.CardExpireException;
import com.esercizio8.demo.helper.CheckingAccountHelper;
import com.esercizio8.demo.helper.UserHelper;
import com.esercizio8.demo.model.CheckingAccount;
import com.esercizio8.demo.model.User;
import com.esercizio8.demo.repository.CheckingAccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckingAccountService {
    private final ModelMapper modelMapper;
    private final CheckingAccountRepository checkingAccountRepository;
    private final CheckingAccountHelper checkingAccountHelper;
    private final UserHelper userHelper;

    public CheckingAccountCreateResponseDto registerCheckingAccount(CheckingAccountCreateRequestDto checkingAccount) {
        checkingAccountHelper.checkIbanAlreadyExists(checkingAccount.getIban());
        User user = userHelper.getUser(checkingAccount.getId_user());
        CheckingAccount checkingAccountSaved = modelMapper.map(checkingAccount, CheckingAccount.class);
        user.assignCheckingAccount(checkingAccountSaved);
        return modelMapper.map(checkingAccountRepository.save(checkingAccountSaved),
                CheckingAccountCreateResponseDto.class);
    }

    public CheckingAccountUpdateResponseDto updateCheckingAccount(CheckingAccountUpdateRequestDto updateCheckingAccount, UUID id) {
        CheckingAccount checkingAccount = checkingAccountHelper.getCheckingAccount(id);

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
        CheckingAccount checkingAccount = checkingAccountHelper.getCheckingAccount(id);

        if(checkingAccount.getValidThru().isBefore(LocalDate.now())) {
            throw new CardExpireException("Card has expired");
        }

        return new CheckingAccountGetBalanceResponseDto(checkingAccount.getBalance());
    }
}

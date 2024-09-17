package io.netstacker.latte.application.services;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import io.netstacker.latte.application.dtos.account.LoginDto;
import io.netstacker.latte.application.dtos.account.RegisterDto;
import io.netstacker.latte.application.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.repositories.IAccountRepository;
import io.netstacker.latte.domain.services.IAccountService;

@Service
public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;
    private final int encodeStrength = 10;

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(long accountId) throws ResourceNotFoundException {
        return accountRepository.findById(accountId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Account not found for this id: " + accountId)
            );
    }

    public Account loginAccount(@Valid LoginDto loginDto) throws BadRequestException {
        var account = accountRepository.findAccountByEmail(loginDto.getEmail()).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account does not exist with this email.");
        }

        var accountPassword = loginDto.getPassword();
        var encodedPassword = new BCryptPasswordEncoder(encodeStrength).encode(accountPassword);
        if(encodedPassword != account.getPassword()) {
            throw new BadRequestException("Invalid password.");
        }
        return account;
    }

    public Account registerAccount(@Valid RegisterDto registerDto) throws ResourceAlreadyExistsException {
        var account = accountRepository.findAccountByEmail(registerDto.getEmail()).orElse(null);
        if (account != null) {
            throw new ResourceAlreadyExistsException("Account already exists with this email.");
        }

        ModelMapper modelMapper = new ModelMapper();
        var newAccount = modelMapper.map(registerDto, Account.class);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(encodeStrength);
        newAccount.setPassword(encoder.encode(newAccount.getPassword()));

        accountRepository.save(newAccount);

        return newAccount;
    }
}
package io.netstacker.latte.domain.services;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import io.netstacker.latte.api.dtos.account.LoginDto;
import io.netstacker.latte.api.dtos.account.RegisterDto;
import io.netstacker.latte.domain.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.domain.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.repositories.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper mapper;
    private final int encodeStrength = 14;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.encoder = new BCryptPasswordEncoder(encodeStrength);
        this.mapper = new ModelMapper();
    }

    public Account getAccountById(long accountId) throws ResourceNotFoundException {
        return accountRepository.findById(accountId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Account not found for this id: " + accountId)
            );
    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    public Account loginAccount(@Valid LoginDto loginDto) throws BadRequestException {
        var account = accountRepository.findAccountByEmail(loginDto.getEmail())
            .orElseThrow(() ->
                new BadRequestException("Account does not exist with this email.")
            );

        var encodedPassword = encoder.encode(loginDto.getPassword());

        if(encoder.matches(encodedPassword, account.getPassword())) {
            throw new BadRequestException("Invalid password.");
        }
        return account;
    }

    public Account registerAccount(@Valid RegisterDto registerDto) throws ResourceAlreadyExistsException {
        var account = accountRepository.findAccountByEmail(registerDto.getEmail()).orElse(null);
        if (account != null) {
            throw new ResourceAlreadyExistsException("Account already exists with this email.");
        }

        var encodedPassword = encoder.encode(registerDto.getPassword());

        var newAccount = mapper.map(registerDto, Account.class);
        newAccount.setPassword(encodedPassword);

        accountRepository.save(newAccount);

        return newAccount;
    }
}
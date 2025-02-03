package io.netstacker.latte.domain.services;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import io.netstacker.latte.api.dtos.account.LoginDto;
import io.netstacker.latte.api.dtos.account.RegisterDto;
import io.netstacker.latte.domain.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.domain.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.repositories.AccountRepository;
import io.netstacker.latte.infra.services.MailService;
import io.netstacker.latte.infra.services.TokenService;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final MailService mailService;
    private final TokenService tokenService;
    private final ModelMapper mapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, MailService mailService, TokenService tokenService) {
        this.accountRepository = accountRepository;
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.mapper = new ModelMapper();
    }

    public Account getAccountById(long accountId) throws ResourceNotFoundException {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + accountId));
    }

    public Account getAccountByEmail(@Valid LoginDto loginDto) throws BadRequestException {
        return accountRepository.findAccountByEmail(loginDto.getEmail())
                .orElseThrow(() -> new BadRequestException("Account does not exist with this email."));
    }

    public String createAuthToken(Account account) {
        return tokenService.createToken(account);
    }

    public int sendAuthToken(String email, String token) {
        String text = String.format("""
                    <!doctype html>
                    <html>
                        <body>
                            <a href="http://%s:%d/api/accounts/login?token=%s">%s</a>
                        </body>
                    </html>
                """, "localhost", 5000, token, "Sign In");
        return mailService.send(email, "Authorization", text);
    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    public Account registerAccount(@Valid RegisterDto registerDto) throws ResourceAlreadyExistsException {
        var account = accountRepository.findAccountByEmail(registerDto.getEmail()).orElse(null);
        if (account != null) {
            throw new ResourceAlreadyExistsException("Account already exists with this email.");
        }
        System.out.println(registerDto.getName());
        var newAccount = mapper.map(registerDto, Account.class);
        accountRepository.save(newAccount);
        System.out.println(newAccount.getName());

        return newAccount;
    }
}
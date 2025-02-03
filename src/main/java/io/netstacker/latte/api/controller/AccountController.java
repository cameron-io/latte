package io.netstacker.latte.api.controller;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.netstacker.latte.domain.services.AccountService;
import io.netstacker.latte.domain.services.MailService;
import io.netstacker.latte.domain.services.TokenService;
import io.netstacker.latte.api.dtos.account.LoginDto;
import io.netstacker.latte.api.dtos.account.RegisterDto;
import io.netstacker.latte.api.dtos.account.UserDto;
import io.netstacker.latte.domain.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.domain.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Account;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final TokenService tokenService;
    private final MailService mailService;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountController(AccountService accountService, TokenService tokenService, MailService mailService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
        this.mailService = mailService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/info")
    public ResponseEntity<Account> getCurrentUser(
            @RequestAttribute("accountId") Long accountId) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        return ResponseEntity.ok().body(account);
    }

    @DeleteMapping("/")
    public ResponseEntity<Map<String, ?>> deleteAccount(
            @RequestAttribute("accountId") Long accountId) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        accountService.deleteAccount(account);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, ?>> loginViaToken(
            @RequestParam("token") String token,
            HttpServletResponse response) {
        var cookie = createLoginCookie(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, ?>> loginAccount(
            @RequestBody LoginDto loginDto) throws BadRequestException {
        var account = accountService.loginAccount(loginDto);
        var token = tokenService.createToken(account);
        String text = String.format("""
                    <!doctype html>
                    <html>
                        <body>
                            <a href="http://%s:%d/api/accounts/login?token=%s">%s</a>
                        </body>
                    </html>
                """, "localhost", 5000, token, "Sign In");
        mailService.send("support@latte.com", "Login", text);
        return ResponseEntity.ok().body(Map.of("msg", "Check your inbox!"));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerAccount(
            @RequestBody RegisterDto registerDto) throws ResourceAlreadyExistsException {
        var account = accountService.registerAccount(registerDto);
        var createdAccount = modelMapper.map(account, UserDto.class);

        return ResponseEntity.ok().body(createdAccount);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, ?>> logoutAccount(
            HttpServletResponse response) throws ResourceAlreadyExistsException {
        var cookie = createLogOutCookie();
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    private Cookie createLoginCookie(String token) {
        var cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 3);
        cookie.setSecure(false); // TODO: Set to true in Production
        return cookie;
    }

    private Cookie createLogOutCookie() {
        var cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setSecure(false); // TODO: Set to true in Production
        return cookie;
    }
}

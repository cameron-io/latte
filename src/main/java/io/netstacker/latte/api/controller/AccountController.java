package io.netstacker.latte.api.controller;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.netstacker.latte.application.dtos.account.LoginDto;
import io.netstacker.latte.application.dtos.account.RegisterDto;
import io.netstacker.latte.application.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.services.ITokenService;
import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.services.IAccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final IAccountService accountService;
    private final ITokenService tokenService;

    @Autowired
    public AccountController(IAccountService accountService, ITokenService tokenService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
    }
    
    @PostMapping("/info")
    public ResponseEntity<Account> getCurrentUser(
        @RequestAttribute("accountId") Long accountId) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, ?>> loginAccount(
        @RequestBody LoginDto loginDto,
        HttpServletResponse response) throws BadRequestException {
        var account = accountService.loginAccount(loginDto);
        var token = tokenService.createToken(account);
        var cookie = tokenCookie(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, ?>> registerAccount(
        @RequestBody RegisterDto registerDto,
        HttpServletResponse response) throws ResourceAlreadyExistsException {
        var account = accountService.registerAccount(registerDto);
        var token = tokenService.createToken(account);
        var cookie = tokenCookie(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    private Cookie tokenCookie(String token) {
        var cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*3);
        return cookie;
    }
}

package io.netstacker.latte.api.controller;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.netstacker.latte.application.dtos.user.LoginDto;
import io.netstacker.latte.application.dtos.user.RegisterDto;
import io.netstacker.latte.application.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.domain.services.ITokenService;
import io.netstacker.latte.domain.services.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;
    private final ITokenService tokenService;
    private final String cookieConfig = "Path=/; HttpOnly; SameSite=strict;";

    @Autowired
    public UserController(IUserService userService, ITokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, ?>> loginUser(
        @RequestBody LoginDto loginDto,
        HttpServletResponse response) throws BadRequestException {
        var user = userService.loginUser(loginDto);
        var token = tokenService.createToken(user);
        var cookie = new Cookie("token", token + "; " + cookieConfig);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, ?>> registerUser(
        @RequestBody RegisterDto registerDto,
        HttpServletResponse response) throws ResourceAlreadyExistsException {
        var user = userService.registerUser(registerDto);
        var token = tokenService.createToken(user);
        var cookie = new Cookie("token", token + "; " + cookieConfig);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}

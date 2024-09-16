package io.netstacker.latte.controller;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.netstacker.latte.dto.LoginDto;
import io.netstacker.latte.dto.RegisterDto;
import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final String cookieConfig = "Path=/; HttpOnly; SameSite=strict;";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, ?>> loginUser(
        @RequestBody LoginDto user,
        HttpServletResponse response) throws BadRequestException {
        var token = userService.loginUser(user);
        var cookie = new Cookie("token", token + "; " + cookieConfig);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, ?>> registerUser(
        @RequestBody RegisterDto user,
        HttpServletResponse response) throws ResourceAlreadyExistsException {
        var token = userService.registerUser(user);
        var cookie = new Cookie("token", token + "; " + cookieConfig);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}

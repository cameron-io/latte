package io.netstacker.latte.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.model.User;
import io.netstacker.latte.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, ?>> createUser(
        @RequestBody User user) throws ResourceAlreadyExistsException {
        String token = userService.registerUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("set-cookie", "token=" + token + "; Path=/; HttpOnly; SameSite=strict;");
        return ResponseEntity
            .ok()
            .headers(responseHeaders)
            .build();
    }
}

package io.netstacker.latte.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.model.User;
import io.netstacker.latte.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, ?>> createUser(@RequestBody User user) throws ResourceAlreadyExistsException {
        Map<String, ?> response = userService.registerUser(user);
        return ResponseEntity.ok().body(response);
    }
}

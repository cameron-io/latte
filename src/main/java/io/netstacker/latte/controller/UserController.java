package io.netstacker.latte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.model.User;
import io.netstacker.latte.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws ResourceAlreadyExistsException {
        userService.createUser(user);
        return ResponseEntity.ok().body(user);
    }
}

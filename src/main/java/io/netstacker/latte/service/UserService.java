package io.netstacker.latte.service;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import io.netstacker.latte.auth.TokenManager;
import io.netstacker.latte.dto.LoginDto;
import io.netstacker.latte.dto.RegisterDto;
import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.User;
import io.netstacker.latte.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final int encodeStrength = 10;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found for this id: " + userId)
            );
    }

    public String loginUser(@Valid LoginDto loginDto) throws BadRequestException {
        var user = userRepository.findUserByEmail(loginDto.getEmail()).orElse(null);
        if (user == null) {
            throw new BadRequestException("User does not exist with this email.");
        }

        var userPassword = loginDto.getPassword();
        var encodedPassword = new BCryptPasswordEncoder(encodeStrength).encode(userPassword);
        if(encodedPassword != user.getPassword()) {
            throw new BadRequestException("Invalid password.");
        }
        
        return TokenManager.createToken(user);
    }

    public String registerUser(@Valid RegisterDto registerDto) throws ResourceAlreadyExistsException {
        var user = userRepository.findUserByEmail(registerDto.getEmail()).orElse(null);
        if (user == null) {
            throw new ResourceAlreadyExistsException("User already exists with this email.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(encodeStrength);
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        return TokenManager.createToken(user);
    }
}
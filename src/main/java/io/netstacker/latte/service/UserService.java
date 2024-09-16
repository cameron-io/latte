package io.netstacker.latte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import io.netstacker.latte.auth.TokenManager;
import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.User;
import io.netstacker.latte.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

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

    public String registerUser(@Valid User user) throws ResourceAlreadyExistsException {
        var userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists with this email.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPassword(encoder.encode(user.getPassword()));
        
        userRepository.save(user);

        // Return session token
        return TokenManager.createToken(user);
    }
}
package io.netstacker.latte.application.services;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import io.netstacker.latte.application.dtos.user.LoginDto;
import io.netstacker.latte.application.dtos.user.RegisterDto;
import io.netstacker.latte.application.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.User;
import io.netstacker.latte.domain.repositories.IUserRepository;
import io.netstacker.latte.domain.services.IUserService;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final int encodeStrength = 10;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found for this id: " + userId)
            );
    }

    public User loginUser(@Valid LoginDto loginDto) throws BadRequestException {
        var user = userRepository.findUserByEmail(loginDto.getEmail()).orElse(null);
        if (user == null) {
            throw new BadRequestException("User does not exist with this email.");
        }

        var userPassword = loginDto.getPassword();
        var encodedPassword = new BCryptPasswordEncoder(encodeStrength).encode(userPassword);
        if(encodedPassword != user.getPassword()) {
            throw new BadRequestException("Invalid password.");
        }
        return user;
    }

    public User registerUser(@Valid RegisterDto registerDto) throws ResourceAlreadyExistsException {
        var user = userRepository.findUserByEmail(registerDto.getEmail()).orElse(null);
        if (user == null) {
            throw new ResourceAlreadyExistsException("User already exists with this email.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(encodeStrength);
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        return user;
    }
}
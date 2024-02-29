package io.netstacker.latte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Map;
import java.util.Date;
import java.util.Optional;

import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.model.User;
import io.netstacker.latte.repository.UserRepository;
import jakarta.validation.Valid;

@Service
public class UserService {
    @Value("${JWT_SECRET}")
    private String jwt_secret;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(@Valid User user) throws ResourceAlreadyExistsException {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists with this email.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        user.setPassword(encoder.encode(user.getPassword()));
        
        userRepository.save(user);

        // Return session token
        Algorithm algorithm = Algorithm.HMAC256(jwt_secret);

        Date ExpiryDate = new Date(System.currentTimeMillis() + 3600000);
        String token = JWT.create()
            .withExpiresAt(ExpiryDate)
            .withPayload(Map.of(
                "user", Map.of(
                    "id", user.getId()
                )
            ))
            .sign(algorithm);

        return token;
    }
}
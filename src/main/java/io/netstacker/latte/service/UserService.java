package io.netstacker.latte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Map;
import static java.util.Map.entry;
import java.util.Date;
import java.util.Optional;

import io.netstacker.latte.exception.ResourceAlreadyExistsException;
import io.netstacker.latte.model.User;
import io.netstacker.latte.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, ?> createUser(User user) throws NullPointerException, ResourceAlreadyExistsException {
        if (user == null) throw new NullPointerException("Created user cannot be null");

        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists with this email.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        user.setPassword(encoder.encode(user.getPassword()));
        
        userRepository.save(user);

        // Return session token

        Algorithm algorithm = Algorithm.HMAC256(System.getenv("JWT_SECRET"));

        Date ExpiryDate = new Date(System.currentTimeMillis() + 360000);
        String token = JWT.create()
            .withExpiresAt(ExpiryDate)
            .sign(algorithm);

        return Map.ofEntries(
            entry("token", token)
        );
    }
}
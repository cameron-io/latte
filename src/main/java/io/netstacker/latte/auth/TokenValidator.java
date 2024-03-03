package io.netstacker.latte.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class TokenValidator {
    @Getter
    private static String jwt_secret;

    @Value("${JWT_SECRET}")
    public void setJwtSecret(String js) { jwt_secret = js; }

    public static Long require(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwt_secret);
        JWTVerifier verifier = JWT.require(algorithm)
            // reusable verifier instance
            .build();
        
        Claim user = verifier.verify(token).getClaim("user");
        Long userId = Long.parseLong(user.asMap().get("id").toString());

        return userId;
    }
}

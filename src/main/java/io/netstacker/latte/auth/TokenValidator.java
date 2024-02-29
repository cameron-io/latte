package io.netstacker.latte.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

public class TokenValidator {
    private String jwt_secret;

    public TokenValidator(String jwt_secret) {
        this.jwt_secret = jwt_secret;
    }

    public Long require(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwt_secret);
        JWTVerifier verifier = JWT.require(algorithm)
            // reusable verifier instance
            .build();
        
        Claim user = verifier.verify(token).getClaim("user");
        Long userId = Long.parseLong(user.asMap().get("id").toString());

        return userId;
    }
}

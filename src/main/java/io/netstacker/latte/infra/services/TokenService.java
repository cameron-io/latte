package io.netstacker.latte.infra.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import io.netstacker.latte.domain.models.Account;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    @Getter
    private static String jwtSecret;

    public Long validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT
                .require(algorithm)
                // reusable verifier instance
                .build();

        Claim account = verifier
                .verify(token)
                .getClaim("account");

        String account_id = account
                .asMap()
                .get("id")
                .toString();

        return Long.parseLong(account_id);
    }

    public String createToken(Account account) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        Date ExpiryDate = new Date(System.currentTimeMillis() + 3600000);
        return JWT.create()
                .withExpiresAt(ExpiryDate)
                .withPayload(Map.of(
                        "account", Map.of(
                                "id", account.getId())))
                .sign(algorithm);
    }

    @Value("${JWT_SECRET}")
    private void setJwtSecret(String js) {
        jwtSecret = js;
    }
}

package io.netstacker.latte.domain.services;

import io.netstacker.latte.domain.models.User;

public interface ITokenService {
    public String createToken(User user);
    public Long validateToken(String token);
}

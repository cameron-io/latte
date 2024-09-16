package io.netstacker.latte.domain.services;

import io.netstacker.latte.domain.models.Account;

public interface ITokenService {
    public String createToken(Account account);
    public Long validateToken(String token);
}

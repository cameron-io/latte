package io.netstacker.latte.domain.services;

import org.apache.coyote.BadRequestException;

import io.netstacker.latte.application.dtos.account.LoginDto;
import io.netstacker.latte.application.dtos.account.RegisterDto;
import io.netstacker.latte.application.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Account;
import jakarta.validation.Valid;

public interface IAccountService {
    public Account getAccountById(long accountId) throws ResourceNotFoundException;

    public Account loginAccount(@Valid LoginDto loginDto) throws BadRequestException;

    public Account registerAccount(@Valid RegisterDto registerDto) throws ResourceAlreadyExistsException;
}

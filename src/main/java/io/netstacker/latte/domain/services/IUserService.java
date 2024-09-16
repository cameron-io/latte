package io.netstacker.latte.domain.services;

import org.apache.coyote.BadRequestException;

import io.netstacker.latte.application.dtos.LoginDto;
import io.netstacker.latte.application.dtos.RegisterDto;
import io.netstacker.latte.application.exceptions.ResourceAlreadyExistsException;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.User;
import jakarta.validation.Valid;

public interface IUserService {
    public User getUserById(long userId) throws ResourceNotFoundException;

    public String loginUser(@Valid LoginDto loginDto) throws BadRequestException;

    public String registerUser(@Valid RegisterDto registerDto) throws ResourceAlreadyExistsException;
}

package io.netstacker.latte.application.dtos;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class LoginDto {
    @NonNull private String email;
    @NonNull private String password;
}

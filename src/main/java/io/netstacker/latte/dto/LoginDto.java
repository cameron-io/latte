package io.netstacker.latte.dto;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class LoginDto {
    @NonNull private String email;
    @NonNull private String password;
}

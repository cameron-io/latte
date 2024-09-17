package io.netstacker.latte.application.dtos.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginDto {
    @NonNull @NotBlank private String email;
    @NonNull @NotBlank private String password;
}

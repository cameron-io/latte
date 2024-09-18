package io.netstacker.latte.application.dtos.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    @NotBlank private String email;
    @NotBlank private String password;
}

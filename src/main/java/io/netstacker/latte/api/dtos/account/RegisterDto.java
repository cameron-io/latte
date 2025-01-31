package io.netstacker.latte.api.dtos.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDto {
    @NotBlank
    @Size(min = 6, max = 64)
    private String name;
    @NotBlank
    @Email
    private String email;
}

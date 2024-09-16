package io.netstacker.latte.application.dtos.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    @NotBlank private String email;
    @NotBlank private String name;
    @NotBlank private String token;
    private String avatar;
}

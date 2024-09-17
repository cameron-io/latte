package io.netstacker.latte.application.dtos.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccountDto {
    @NonNull @NotNull private Long id;
    @NonNull @NotBlank private String name;
    @NonNull @NotBlank private String email;
    @NonNull @NotBlank private String token;
    private String avatar;
}

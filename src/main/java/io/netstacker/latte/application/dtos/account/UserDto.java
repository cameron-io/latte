package io.netstacker.latte.application.dtos.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    @NotNull private Long id;
    @NotBlank private String name;
    @NotBlank private String email;
    @NotBlank private String token;
    private String avatar;
}

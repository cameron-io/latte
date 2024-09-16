package io.netstacker.latte.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @Size(min = 6, max = 64)
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @Size(min = 6, max = 255)
    @NotBlank
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    private String avatar;
}

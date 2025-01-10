package io.netstacker.latte.api.dtos.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @JsonProperty(access = Access.WRITE_ONLY)
    @Pattern(
        regexp = "(?=^.{6,10}$)(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\\\s).*$",
        message =
            "Password must have at least: " +
            "1 Uppercase, 1 Lowercase, 1 Number, 1 Special Character " + 
            "and 6 characters")
    private String password;
}

package io.netstacker.latte.application.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @Size(min = 6, max = 64)
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @Size(min = 6, max = 255)
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

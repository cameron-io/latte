package io.netstacker.latte.application.dtos.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExperienceDto {
    public Long id;
    @NotBlank public String title;
    @NotBlank public String company;
    @NotBlank public String from;
    public String location;
    public String to;
    public boolean current;
    public String description;
}

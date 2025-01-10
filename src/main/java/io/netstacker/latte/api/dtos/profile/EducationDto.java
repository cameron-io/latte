package io.netstacker.latte.api.dtos.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EducationDto {
    private Long id;
    @NotBlank private String school;
    @NotBlank private String degree;
    @NotBlank private String fieldOfStudy;
    @NotBlank private String from;
    public String to = "";
    public boolean current = true;
    public String description = "";
}

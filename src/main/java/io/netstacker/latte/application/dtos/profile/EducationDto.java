package io.netstacker.latte.application.dtos.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EducationDto {
    private Long id;
    @NotBlank private String school;
    @NotBlank private String degree;
    @NotBlank private String fieldOfStudy;
    @NotBlank private String from;
    public String to;
    public boolean current;
    public String description;
}

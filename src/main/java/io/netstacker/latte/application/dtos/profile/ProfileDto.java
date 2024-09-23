package io.netstacker.latte.application.dtos.profile;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDto {
    private Long id;
    @NotBlank private String status;
    @NotNull private List<String> skills;

    private String company;
    private String website;
    private String location;
    private String bio;
    private String gitHubUsername;
    private List<ExperienceDto> experience;
    private List<EducationDto> education;
    private List<SocialDto> social;
    private Long accountId;
}

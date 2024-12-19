package io.netstacker.latte.application.dtos.profile;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.netstacker.latte.application.dtos.account.UserDto;
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
    private List<ExperienceDto> experience = Collections.emptyList();
    private List<EducationDto> education = Collections.emptyList();
    private SocialDto social;
    @JsonProperty("user")
    private UserDto account;
}

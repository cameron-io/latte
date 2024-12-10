package io.netstacker.latte.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.application.dtos.profile.EducationDto;
import io.netstacker.latte.application.dtos.profile.ExperienceDto;
import io.netstacker.latte.application.dtos.profile.ProfileCreateDto;
import io.netstacker.latte.application.dtos.profile.ProfileGetDto;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.application.specifications.ProfileSpecifications;
import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.models.Education;
import io.netstacker.latte.domain.models.Experience;
import io.netstacker.latte.domain.models.Profile;
import io.netstacker.latte.domain.repositories.IEducationRepository;
import io.netstacker.latte.domain.repositories.IExperienceRepository;
import io.netstacker.latte.domain.repositories.IProfileRepository;
import jakarta.validation.Valid;

@Service
public class ProfileService {
    private IProfileRepository profileRepository;
    private IExperienceRepository experienceRepository;
    private IEducationRepository educationRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProfileService(
        IProfileRepository profileRepository,
        IExperienceRepository experienceRepository,
        IEducationRepository educationRepository) {
        this.profileRepository = profileRepository;
        this.experienceRepository = experienceRepository;
        this.educationRepository = educationRepository;
    }

    public List<ProfileGetDto> getAllProfiles() {
        var profiles = profileRepository.findAll();
        var profileDtos = profiles
            .stream()
            .map((profile) -> modelMapper.map(profile, ProfileGetDto.class))
            .collect(Collectors.toList());
        return profileDtos;
    }

    public ProfileGetDto getProfileById(long profileId) throws ResourceNotFoundException {
        var profile = profileRepository.findById(profileId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Profile not found for this id: " + profileId)
            );
        return modelMapper.map(profile, ProfileGetDto.class);
    }

    public ProfileGetDto getProfileByAccount(Account account) throws ResourceNotFoundException {
        var spec = ProfileSpecifications.ProfileByAccountId(account);
        Profile profile = profileRepository.findOne(spec).orElseThrow(() ->
            new ResourceNotFoundException("Profile not found for this account")
        );
        return modelMapper.map(profile, ProfileGetDto.class);
    }

    public ProfileGetDto upsertProfile(Account account, @Valid ProfileCreateDto profileDto) throws NullPointerException {
        var validatedProfile = modelMapper.map(profileDto, Profile.class);
        validatedProfile.setAccount(account);
        var newProfile = profileRepository.save(validatedProfile);
        return modelMapper.map(newProfile, ProfileGetDto.class);
    }

    public ExperienceDto upsertExperience(Account account, @Valid ExperienceDto experienceDto) throws NullPointerException {
        var validatedExperience = modelMapper.map(experienceDto, Experience.class);
        var newExperience = experienceRepository.save(validatedExperience);
        return modelMapper.map(newExperience, ExperienceDto.class);
    }

    public EducationDto upsertEducation(Account account, @Valid EducationDto educationDto) throws NullPointerException {
        var validatedEducation = modelMapper.map(educationDto, Education.class);
        var newEducation = educationRepository.save(validatedEducation);
        return modelMapper.map(newEducation, EducationDto.class);
    }
}

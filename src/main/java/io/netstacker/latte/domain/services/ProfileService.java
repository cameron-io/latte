package io.netstacker.latte.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.api.dtos.profile.EducationDto;
import io.netstacker.latte.api.dtos.profile.ExperienceDto;
import io.netstacker.latte.api.dtos.profile.ProfileDto;
import io.netstacker.latte.domain.specifications.ProfileSpecifications;
import io.netstacker.latte.domain.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.models.Education;
import io.netstacker.latte.domain.models.Experience;
import io.netstacker.latte.domain.models.Profile;
import io.netstacker.latte.domain.repositories.EducationRepository;
import io.netstacker.latte.domain.repositories.ExperienceRepository;
import io.netstacker.latte.domain.repositories.ProfileRepository;
import jakarta.validation.Valid;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;
    private ExperienceRepository experienceRepository;
    private EducationRepository educationRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProfileService(
            ProfileRepository profileRepository,
            ExperienceRepository experienceRepository,
            EducationRepository educationRepository) {
        this.profileRepository = profileRepository;
        this.experienceRepository = experienceRepository;
        this.educationRepository = educationRepository;
    }

    public List<ProfileDto> getAllProfiles() {
        var profiles = profileRepository.findAll();
        var profileDtos = profiles
                .stream()
                .map((profile) -> modelMapper.map(profile, ProfileDto.class))
                .collect(Collectors.toList());
        return profileDtos;
    }

    public ProfileDto getProfileById(long profileId) throws ResourceNotFoundException {
        var profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for this id: " + profileId));
        return modelMapper.map(profile, ProfileDto.class);
    }

    public ProfileDto getProfileByAccount(Account account) throws ResourceNotFoundException {
        var spec = ProfileSpecifications.profileByAccountId(account);
        Profile profile = profileRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for this account"));
        return modelMapper.map(profile, ProfileDto.class);
    }

    public ProfileDto upsertProfile(Account account, @Valid ProfileDto profileDto) throws NullPointerException {
        var validatedProfile = modelMapper.map(profileDto, Profile.class);
        validatedProfile.setAccount(account);
        var newProfile = profileRepository.save(validatedProfile);
        return modelMapper.map(newProfile, ProfileDto.class);
    }

    public ExperienceDto upsertExperience(Account account, @Valid ExperienceDto experienceDto)
            throws NullPointerException {
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

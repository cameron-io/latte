package io.netstacker.latte.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.application.dtos.profile.ProfileDto;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.application.specifications.ProfileSpecifications;
import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.models.Profile;
import io.netstacker.latte.domain.repositories.IProfileRepository;
import jakarta.validation.Valid;

@Service
public class ProfileService {
    private IProfileRepository profileRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProfileService(IProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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
            .orElseThrow(() ->
                new ResourceNotFoundException("Profile not found for this id: " + profileId)
            );
        return modelMapper.map(profile, ProfileDto.class);
    }

    public ProfileDto getProfileByAccount(Account account) throws ResourceNotFoundException {
        var spec = ProfileSpecifications.ProfileByAccountId(account);
        Profile profile = profileRepository.findOne(spec).orElseThrow(() ->
            new ResourceNotFoundException("Profile not found for this account")
        );
        return modelMapper.map(profile, ProfileDto.class);
    }

    public void createProfile(Account account, @Valid ProfileDto profileDto) throws NullPointerException {
        var profile = modelMapper.map(profileDto, Profile.class);
        profile.setAccount(account);
        profileRepository.save(profile);
    }
}

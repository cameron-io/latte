package io.netstacker.latte.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Profile;
import io.netstacker.latte.domain.repositories.IProfileRepository;
import jakarta.validation.Valid;

@Service
public class ProfileService {
    private IProfileRepository profileRepository;

    @Autowired
    public ProfileService(IProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfileById(long profileId) throws ResourceNotFoundException {
        return profileRepository.findById(profileId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Profile not found for this id: " + profileId)
            );
    }

    public void createProfile(@Valid Profile profile) throws NullPointerException {
        if (profile == null) throw new NullPointerException("Created profile cannot be null");
        profileRepository.save(profile);
    }
}

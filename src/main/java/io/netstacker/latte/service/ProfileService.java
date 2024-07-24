package io.netstacker.latte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.Profile;
import io.netstacker.latte.repository.ProfileRepository;
import jakarta.validation.Valid;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository ProfileRepository) {
        profileRepository = ProfileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfileById(long profileId)
    throws ResourceNotFoundException {
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

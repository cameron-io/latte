package io.netstacker.latte.domain.services;

import java.util.List;

import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Profile;
import jakarta.validation.Valid;

public interface IProfileService {
    
    public List<Profile> getAllProfiles();

    public Profile getProfileById(long profileId) throws ResourceNotFoundException;

    public void createProfile(@Valid Profile profile) throws NullPointerException;
}

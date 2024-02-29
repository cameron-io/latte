package io.netstacker.latte.service;

import org.springframework.beans.factory.annotation.Autowired;

import io.netstacker.latte.repository.ProfileRepository;

public class ProfileService {
    private ProfileRepository ProfileRepository;

    @Autowired
    public ProfileService(ProfileRepository ProfileRepository) {
        this.ProfileRepository = ProfileRepository;
    }
}

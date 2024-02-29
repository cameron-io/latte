package io.netstacker.latte.controller;

import org.springframework.beans.factory.annotation.Autowired;

import io.netstacker.latte.service.ProfileService;

public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
}

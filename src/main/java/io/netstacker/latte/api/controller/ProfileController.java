package io.netstacker.latte.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.application.services.ProfileService;
import io.netstacker.latte.domain.services.IAccountService;
import io.netstacker.latte.domain.models.Profile;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final IAccountService accountService;

    @Autowired
    public ProfileController(
        ProfileService profileService,
        IAccountService accountService) {
        this.profileService = profileService;
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        var profiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(
        @PathVariable(value = "id") long profileId) throws ResourceNotFoundException {
        var profile = profileService.getProfileById(profileId);
        return ResponseEntity.ok().body(profile);
    }

    @PostMapping("/")
    public ResponseEntity<Profile> createProfile(
        @RequestAttribute("accountId") Long accountId,
        @RequestBody Profile profile) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        profile.setAccount(account);
        profileService.createProfile(profile);
        return ResponseEntity.ok().body(profile);
    }
}

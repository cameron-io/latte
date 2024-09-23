package io.netstacker.latte.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.netstacker.latte.application.dtos.profile.ProfileDto;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.application.services.ProfileService;
import io.netstacker.latte.domain.services.IAccountService;

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

    @GetMapping()
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        var profiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfileById(
        @PathVariable(value = "id") long profileId) throws ResourceNotFoundException {
        var profile = profileService.getProfileById(profileId);
        return ResponseEntity.ok().body(profile);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileDto> getMe(
        @RequestAttribute("accountId") Long accountId) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        var profile = profileService.getProfileByAccount(account);
        return ResponseEntity.ok().body(profile);
    }

    @PostMapping()
    public ResponseEntity<ProfileDto> createProfile(
        @RequestAttribute("accountId") Long accountId,
        @RequestBody ProfileDto profileDto) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        profileService.createProfile(account, profileDto);
        return ResponseEntity.ok().body(profileDto);
    }
}

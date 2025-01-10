package io.netstacker.latte.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.netstacker.latte.api.dtos.profile.ExperienceDto;
import io.netstacker.latte.api.dtos.profile.ProfileDto;
import io.netstacker.latte.domain.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.services.ProfileService;
import io.netstacker.latte.domain.services.AccountService;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final AccountService accountService;

    @Autowired
    public ProfileController(
        ProfileService profileService,
        AccountService accountService) {
        this.profileService = profileService;
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        var profileDtos = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profileDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfileById(
        @PathVariable(value = "id") long profileId
    ) throws ResourceNotFoundException {
        var profileDto = profileService.getProfileById(profileId);
        return ResponseEntity.ok().body(profileDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileDto> getMe(
        @RequestAttribute("accountId") Long accountId
    ) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        var profileDto = profileService.getProfileByAccount(account);
        return ResponseEntity.ok().body(profileDto);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ProfileDto> getMe(
        @PathVariable(value = "id") long accountId
    ) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        var profileDto = profileService.getProfileByAccount(account);
        return ResponseEntity.ok().body(profileDto);
    }

    @PostMapping()
    @PutMapping()
    public ResponseEntity<ProfileDto> upsertProfile(
        @RequestAttribute("accountId") Long accountId,
        @RequestBody ProfileDto profileCreateDto
    ) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        var profileDto = profileService.upsertProfile(account, profileCreateDto);
        return ResponseEntity.ok().body(profileDto);
    }

    @PutMapping("/experience")
    public ResponseEntity<ProfileDto> upsertExperience(
        @RequestAttribute("accountId") Long accountId,
        @RequestBody ExperienceDto experienceDto
    ) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        profileService.upsertExperience(account, experienceDto);
        var profileDto = profileService.getProfileByAccount(account);
        return ResponseEntity.ok().body(profileDto);
    }
}

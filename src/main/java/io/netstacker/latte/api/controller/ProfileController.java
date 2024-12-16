package io.netstacker.latte.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.netstacker.latte.application.dtos.profile.ProfileCreateDto;
import io.netstacker.latte.application.dtos.profile.ProfileGetDto;
import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.application.services.ProfileService;
import io.netstacker.latte.application.services.AccountService;

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
    public ResponseEntity<List<ProfileGetDto>> getAllProfiles() {
        var profileDtos = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profileDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileGetDto> getProfileById(
        @PathVariable(value = "id") long profileId) throws ResourceNotFoundException {
        var profileDto = profileService.getProfileById(profileId);
        return ResponseEntity.ok().body(profileDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileGetDto> getMe(
        @RequestAttribute("accountId") Long accountId) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        var profileDto = profileService.getProfileByAccount(account);
        return ResponseEntity.ok().body(profileDto);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ProfileGetDto> getMe(
        @PathVariable(value = "id") long accountId) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        var profileDto = profileService.getProfileByAccount(account);
        return ResponseEntity.ok().body(profileDto);
    }

    @PostMapping()
    @PutMapping()
    public ResponseEntity<ProfileGetDto> upsertProfile(
        @RequestAttribute("accountId") Long accountId,
        @RequestBody ProfileCreateDto profileCreateDto) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        var profileDto = profileService.upsertProfile(account, profileCreateDto);
        return ResponseEntity.ok().body(profileDto);
    }
}

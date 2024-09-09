package io.netstacker.latte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.netstacker.latte.auth.TokenManager;
import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.Profile;
import io.netstacker.latte.model.User;
import io.netstacker.latte.service.ProfileService;
import io.netstacker.latte.service.UserService;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    public ProfileController(
        ProfileService profileService,
        UserService userService
    ) {
        this.profileService = profileService;
        this.userService = userService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(
        @PathVariable(value = "id") long profileId
    ) throws ResourceNotFoundException {
        Profile profile = profileService.getProfileById(profileId);
        return ResponseEntity.ok().body(profile);
    }

    @PostMapping("/")
    public ResponseEntity<Profile> createProfile(
        @CookieValue("token") String token,
        @RequestBody Profile profile
    ) throws ResourceNotFoundException {
        Long userId = TokenManager.validateToken(token);
        User user = userService.getUserById(userId);
        profile.setUser(user);
        profileService.createProfile(profile);
        return ResponseEntity.ok().body(profile);
    }
}

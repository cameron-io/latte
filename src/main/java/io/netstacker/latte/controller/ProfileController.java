package io.netstacker.latte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.netstacker.latte.service.ProfileService;
import io.netstacker.latte.service.UserService;

@RestController
@RequestMapping("/api/blogs")
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
    
}

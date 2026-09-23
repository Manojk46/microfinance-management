package com.project.microfinance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.project.microfinance.dto.ProfileRequest;
import com.project.microfinance.entity.BorrowerProfile;
import com.project.microfinance.service.BorrowerProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerProfileController {

    private final BorrowerProfileService profileService;

    public BorrowerProfileController(
            BorrowerProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/profile")
    public ResponseEntity<BorrowerProfile> createOrUpdateProfile(
            @Valid @RequestBody ProfileRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        BorrowerProfile profile =
                profileService.createOrUpdateProfile(email, request);

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile")
    public ResponseEntity<BorrowerProfile> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                profileService.getProfile(email)
        );
    }
}
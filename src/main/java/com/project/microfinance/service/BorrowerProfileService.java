package com.project.microfinance.service;

import org.springframework.stereotype.Service;

import com.project.microfinance.dto.ProfileRequest;
import com.project.microfinance.entity.BorrowerProfile;
import com.project.microfinance.entity.User;
import com.project.microfinance.repository.BorrowerProfileRepository;
import com.project.microfinance.repository.UserRepository;

@Service
public class BorrowerProfileService {

    private final UserRepository userRepository;
    private final BorrowerProfileRepository profileRepository;

    public BorrowerProfileService(
            UserRepository userRepository,
            BorrowerProfileRepository profileRepository) {

        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public BorrowerProfile createOrUpdateProfile(
            String email,
            ProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        BorrowerProfile profile =
                profileRepository.findByUser(user)
                .orElse(new BorrowerProfile());

        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setOccupation(request.getOccupation());
        profile.setMonthlyIncome(request.getMonthlyIncome());
        profile.setUser(user);

        return profileRepository.save(profile);
    }

    public BorrowerProfile getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        return profileRepository.findByUser(user)
                .orElseThrow(() ->
                    new RuntimeException("Profile not found"));
    }
}
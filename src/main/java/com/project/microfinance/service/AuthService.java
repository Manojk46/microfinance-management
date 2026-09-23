package com.project.microfinance.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.microfinance.dto.LoginRequest;
import com.project.microfinance.dto.LoginResponse;
import com.project.microfinance.dto.RegisterRequest;
import com.project.microfinance.entity.Role;
import com.project.microfinance.entity.User;
import com.project.microfinance.repository.RoleRepository;
import com.project.microfinance.repository.UserRepository;
import com.project.microfinance.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Registration
    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already registered";
        }

        Role borrowerRole = roleRepository.findByName("BORROWER")
                .orElseThrow(() -> new RuntimeException("BORROWER role not found"));

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(borrowerRole);

        userRepository.save(user);

        return "Registration successful";
    }

    // Login
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getRole().getName()
        );
    }
}
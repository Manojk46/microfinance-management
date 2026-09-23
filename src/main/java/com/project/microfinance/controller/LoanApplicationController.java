package com.project.microfinance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.project.microfinance.dto.LoanApplicationRequest;
import com.project.microfinance.service.LoanApplicationService;
import com.project.microfinance.dto.LoanApplicationResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(
            LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponse> applyForLoan(
            @Valid @RequestBody LoanApplicationRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        LoanApplicationResponse application =
                loanApplicationService.applyForLoan(
                        email, request);

        return ResponseEntity.ok(application);
    }

    @GetMapping("/my")
    public ResponseEntity<List<LoanApplicationResponse>> getMyApplications(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                loanApplicationService.getMyApplications(email)
        );
    }
}
package com.project.microfinance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.microfinance.dto.LoanApplicationResponse;
import com.project.microfinance.entity.LoanApplication;
import com.project.microfinance.service.AdminLoanService;

@RestController
@RequestMapping("/api/admin/loans")
public class AdminLoanController {

    private final AdminLoanService adminLoanService;

    public AdminLoanController(AdminLoanService adminLoanService) {
        this.adminLoanService = adminLoanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponse>> getAllApplications() {
        return ResponseEntity.ok(adminLoanService.getAllApplications());
    }

    @PutMapping("/{loanId}/approve")
    public ResponseEntity<LoanApplicationResponse> approveLoan(
            @PathVariable Long loanId) {

        return ResponseEntity.ok(
                adminLoanService.approveLoan(loanId)
        );
    }
    @PutMapping("/{loanId}/reject")
    public ResponseEntity<LoanApplicationResponse> rejectLoan(
            @PathVariable Long loanId) {

        return ResponseEntity.ok(
                adminLoanService.rejectLoan(loanId)
        );
    }
}
package com.project.microfinance.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.microfinance.entity.LoanApplication;
import com.project.microfinance.repository.LoanApplicationRepository;
import com.project.microfinance.dto.LoanApplicationResponse;

@Service
public class AdminLoanService {

    private final LoanApplicationRepository loanApplicationRepository;

    public AdminLoanService(
            LoanApplicationRepository loanApplicationRepository) {

        this.loanApplicationRepository = loanApplicationRepository;
    }

    public List<LoanApplicationResponse> getAllApplications() {

        return loanApplicationRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public LoanApplicationResponse approveLoan(Long loanId) {

        LoanApplication application =
                loanApplicationRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan application not found"));

        if (!application.getLoanStatus().equals("PENDING")) {
            throw new RuntimeException(
                    "Only pending loans can be approved");
        }

        application.setLoanStatus("APPROVED");
        application.setReviewedAt(LocalDateTime.now());

        LoanApplication savedApplication =
                loanApplicationRepository.save(application);

        return convertToResponse(savedApplication);
    }

    public LoanApplicationResponse rejectLoan(Long loanId) {

        LoanApplication application =
                loanApplicationRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan application not found"));

        if (!application.getLoanStatus().equals("PENDING")) {
            throw new RuntimeException(
                    "Only pending loans can be rejected");
        }

        application.setLoanStatus("REJECTED");
        application.setReviewedAt(LocalDateTime.now());

        LoanApplication savedApplication =
                loanApplicationRepository.save(application);

        return convertToResponse(savedApplication);
      }
    
    private LoanApplicationResponse convertToResponse(
            LoanApplication application) {

        return new LoanApplicationResponse(
                application.getId(),
                application.getLoanType().getName(),
                application.getRequestedAmount(),
                application.getTenureMonths(),
                application.getPurpose(),
                application.getEligibilityScore(),
                application.getEligibilityStatus(),
                application.getLoanStatus(),
                application.getAppliedAt(),
                application.getReviewedAt()
        );
    }
}
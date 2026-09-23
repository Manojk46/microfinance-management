package com.project.microfinance.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.microfinance.dto.LoanApplicationRequest;
import com.project.microfinance.entity.LoanApplication;
import com.project.microfinance.entity.LoanType;
import com.project.microfinance.entity.User;
import com.project.microfinance.repository.LoanApplicationRepository;
import com.project.microfinance.repository.LoanTypeRepository;
import com.project.microfinance.repository.UserRepository;
import com.project.microfinance.entity.BorrowerProfile;
import com.project.microfinance.repository.BorrowerProfileRepository;
import com.project.microfinance.dto.LoanApplicationResponse;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final UserRepository userRepository;
    private final BorrowerProfileRepository profileRepository;

    public LoanApplicationService(
            LoanApplicationRepository loanApplicationRepository,
            LoanTypeRepository loanTypeRepository,
            UserRepository userRepository,
            BorrowerProfileRepository profileRepository) {

        this.loanApplicationRepository = loanApplicationRepository;
        this.loanTypeRepository = loanTypeRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public LoanApplicationResponse applyForLoan(
            String email,
            LoanApplicationRequest request) {

        // Find borrower
        User borrower = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Find loan type
        LoanType loanType = loanTypeRepository
                .findById(request.getLoanTypeId())
                .orElseThrow(() ->
                        new RuntimeException("Loan type not found"));

        // Validate loan amount
        if (request.getRequestedAmount() > loanType.getMaxAmount()) {
            throw new RuntimeException(
                    "Requested amount exceeds maximum loan amount");
        }

        // Validate tenure
        if (request.getTenureMonths() >
                loanType.getMaxTenureMonths()) {

            throw new RuntimeException(
                    "Requested tenure exceeds maximum allowed tenure");
        }

        // Create application
        LoanApplication application = new LoanApplication();

        application.setBorrower(borrower);
        application.setLoanType(loanType);
        application.setRequestedAmount(
                request.getRequestedAmount());
        application.setTenureMonths(
                request.getTenureMonths());
        application.setPurpose(request.getPurpose());

        // Montly salary eligibility calculation
        BorrowerProfile profile = profileRepository.findByUser(borrower)
                .orElseThrow(() ->
                        new RuntimeException("Borrower profile not found"));

        double monthlyIncome = profile.getMonthlyIncome();

        double maximumEligibleAmount = monthlyIncome * 10;

        double score;

        if (monthlyIncome >= 20000
                && request.getRequestedAmount() <= maximumEligibleAmount) {

            score = 80;

        } else if (monthlyIncome >= 10000
                && request.getRequestedAmount() <= maximumEligibleAmount) {

            score = 60;

        } else {

            score = 40;
        }

        application.setEligibilityScore(score);

        if (score >= 50) {
            application.setEligibilityStatus("ELIGIBLE");
        } else {
            application.setEligibilityStatus("NOT_ELIGIBLE");
        }

        application.setLoanStatus("PENDING");
        application.setAppliedAt(LocalDateTime.now());

        LoanApplication savedApplication =
                loanApplicationRepository.save(application);

        return convertToResponse(savedApplication);
    }

    public List<LoanApplicationResponse> getMyApplications(String email) {

        User borrower = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return loanApplicationRepository.findByBorrower(borrower)
                .stream()
                .map(this::convertToResponse)
                .toList();
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
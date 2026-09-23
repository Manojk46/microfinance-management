package com.project.microfinance.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanApplicationResponse {

    private Long id;

    private String loanType;

    private Double requestedAmount;

    private Integer tenureMonths;

    private String purpose;

    private Double eligibilityScore;

    private String eligibilityStatus;

    private String loanStatus;

    private LocalDateTime appliedAt;

    private LocalDateTime reviewedAt;
}
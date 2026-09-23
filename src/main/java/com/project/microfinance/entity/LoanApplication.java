package com.project.microfinance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loan_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "loan_type_id", nullable = false)
    private LoanType loanType;

    private Double requestedAmount;

    private Integer tenureMonths;

    private String purpose;

    private Double eligibilityScore;

    private String eligibilityStatus;

    private String loanStatus;

    private LocalDateTime appliedAt;

    private LocalDateTime reviewedAt;
}
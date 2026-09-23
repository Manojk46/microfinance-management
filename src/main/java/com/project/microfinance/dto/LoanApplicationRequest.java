package com.project.microfinance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanApplicationRequest {

    @NotNull
    private Long loanTypeId;

    @NotNull
    @Positive
    private Double requestedAmount;

    @NotNull
    @Positive
    private Integer tenureMonths;

    @NotBlank
    private String purpose;
}
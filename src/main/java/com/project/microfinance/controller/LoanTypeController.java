package com.project.microfinance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.project.microfinance.entity.LoanType;
import com.project.microfinance.repository.LoanTypeRepository;

@RestController
@RequestMapping("/api/loan-types")
public class LoanTypeController {

    private final LoanTypeRepository loanTypeRepository;

    public LoanTypeController(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    @GetMapping
    public List<LoanType> getAllLoanTypes() {
        return loanTypeRepository.findAll();
    }
}
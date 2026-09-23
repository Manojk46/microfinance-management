package com.project.microfinance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microfinance.entity.LoanApplication;
import com.project.microfinance.entity.User;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByBorrower(User borrower);
}
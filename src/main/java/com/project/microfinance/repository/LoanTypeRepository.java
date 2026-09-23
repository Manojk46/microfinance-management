package com.project.microfinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microfinance.entity.LoanType;

public interface LoanTypeRepository
        extends JpaRepository<LoanType, Long> {
}
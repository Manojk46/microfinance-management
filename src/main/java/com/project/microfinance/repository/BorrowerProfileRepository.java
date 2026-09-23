package com.project.microfinance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microfinance.entity.BorrowerProfile;
import com.project.microfinance.entity.User;

public interface BorrowerProfileRepository
        extends JpaRepository<BorrowerProfile, Long> {

    Optional<BorrowerProfile> findByUser(User user);
}
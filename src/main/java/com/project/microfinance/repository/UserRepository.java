package com.project.microfinance.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.microfinance.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
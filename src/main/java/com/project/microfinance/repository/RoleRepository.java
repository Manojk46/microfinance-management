package com.project.microfinance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microfinance.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

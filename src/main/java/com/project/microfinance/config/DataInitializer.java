package com.project.microfinance.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.microfinance.entity.LoanType;
import com.project.microfinance.entity.Role;
import com.project.microfinance.entity.User;
import com.project.microfinance.repository.LoanTypeRepository;
import com.project.microfinance.repository.RoleRepository;
import com.project.microfinance.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            LoanTypeRepository loanTypeRepository) {

        return args -> {

            // Roles
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ADMIN"));
            }

            if (roleRepository.findByName("BORROWER").isEmpty()) {
                roleRepository.save(new Role(null, "BORROWER"));
            }

            if (roleRepository.findByName("LENDER").isEmpty()) {
                roleRepository.save(new Role(null, "LENDER"));
            }

            // Loan Types
            if (loanTypeRepository.count() == 0) {

                loanTypeRepository.save(
                    new LoanType(
                        null,
                        "Business Loan",
                        200000.0,
                        24,
                        12.0
                    )
                );

                loanTypeRepository.save(
                    new LoanType(
                        null,
                        "Agriculture Loan",
                        150000.0,
                        18,
                        10.0
                    )
                );

                loanTypeRepository.save(
                    new LoanType(
                        null,
                        "Personal Loan",
                        100000.0,
                        12,
                        14.0
                    )
                );
            }
        };
    }
    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

                Role adminRole = roleRepository.findByName("ADMIN")
                        .orElseThrow(() ->
                                new RuntimeException("ADMIN role not found"));

                User admin = new User();

                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );
                admin.setRole(adminRole);

                userRepository.save(admin);
            }
        };
    }
}
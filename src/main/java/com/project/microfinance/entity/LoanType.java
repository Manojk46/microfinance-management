package com.project.microfinance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loan_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double maxAmount;

    private Integer maxTenureMonths;

    private Double interestRate;
}
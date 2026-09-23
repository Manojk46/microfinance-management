package com.project.microfinance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "borrower_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String address;

    private String occupation;

    private Double monthlyIncome;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
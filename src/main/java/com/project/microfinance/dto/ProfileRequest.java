package com.project.microfinance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    @NotBlank
    private String occupation;

    @NotNull
    private Double monthlyIncome;
}
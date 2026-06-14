package com.shravan.frauddetection.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Average transaction amount is required")
    @DecimalMin(
        value = "0.01",
        message = "Average transaction amount must be positive"
    )
    private BigDecimal avgTransactionAmount;
}
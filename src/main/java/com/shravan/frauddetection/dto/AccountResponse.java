package com.shravan.frauddetection.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountResponse {

    private Long id;

    private String accountHolderName;

    private String accountNumber;

    private BigDecimal avgTransactionAmount;
}
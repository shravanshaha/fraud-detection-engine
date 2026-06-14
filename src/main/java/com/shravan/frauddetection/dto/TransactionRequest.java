package com.shravan.frauddetection.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

    private String accountNumber;

    private BigDecimal amount;

    private String currency;

    private String merchantName;

    private String merchantCategory;

    private String ipAddress;
}
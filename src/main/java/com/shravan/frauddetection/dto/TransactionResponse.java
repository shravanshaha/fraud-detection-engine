package com.shravan.frauddetection.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionResponse {

    private Long transactionId;

    private int riskScore;

    private String decision;

    private String message;
}
package com.shravan.frauddetection.controller;

import org.springframework.web.bind.annotation.*;

import com.shravan.frauddetection.dto.TransactionRequest;
import com.shravan.frauddetection.dto.TransactionResponse;
import com.shravan.frauddetection.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionResponse createTransaction(
            @RequestBody TransactionRequest request) {

        return transactionService.processTransaction(request);
    }
}
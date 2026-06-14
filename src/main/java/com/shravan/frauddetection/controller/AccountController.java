package com.shravan.frauddetection.controller;

import org.springframework.web.bind.annotation.*;

import com.shravan.frauddetection.dto.AccountRequest;
import com.shravan.frauddetection.dto.AccountResponse;
import com.shravan.frauddetection.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(
            AccountService accountService) {

        this.accountService = accountService;
    }


    @PostMapping
    public AccountResponse createAccount(
            @Valid @RequestBody AccountRequest request) {

        return accountService.createAccount(request);
    }


    @GetMapping("/{id}")
    public AccountResponse getAccount(
            @PathVariable Long id) {

        return accountService.getAccount(id);
    }
}
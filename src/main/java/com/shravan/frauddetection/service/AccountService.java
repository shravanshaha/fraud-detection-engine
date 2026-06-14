package com.shravan.frauddetection.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.shravan.frauddetection.dto.AccountRequest;
import com.shravan.frauddetection.dto.AccountResponse;
import com.shravan.frauddetection.exception.ResourceNotFoundException;
import com.shravan.frauddetection.model.entity.Account;
import com.shravan.frauddetection.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse createAccount(AccountRequest request) {

        Account account = new Account();

        account.setAccountHolderName(
                request.getAccountHolderName());

        account.setAccountNumber(
                request.getAccountNumber());

        account.setAvgTransactionAmount(
                request.getAvgTransactionAmount());

        account.setCreatedAt(LocalDateTime.now());

        Account savedAccount =
                accountRepository.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getAccountHolderName(),
                savedAccount.getAccountNumber(),
                savedAccount.getAvgTransactionAmount()
        );
    }


    public AccountResponse getAccount(Long id) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Account not found"));

        return new AccountResponse(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountNumber(),
                account.getAvgTransactionAmount()
        );
    }
}
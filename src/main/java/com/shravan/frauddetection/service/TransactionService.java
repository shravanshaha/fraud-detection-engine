package com.shravan.frauddetection.service;

import com.shravan.frauddetection.model.entity.RuleEvaluation;
import com.shravan.frauddetection.repository.RuleEvaluationRepository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shravan.frauddetection.dto.TransactionRequest;
import com.shravan.frauddetection.dto.TransactionResponse;
import com.shravan.frauddetection.exception.ResourceNotFoundException;
import com.shravan.frauddetection.model.entity.Account;
import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.model.entity.TransactionStatus;
import com.shravan.frauddetection.repository.AccountRepository;
import com.shravan.frauddetection.repository.TransactionRepository;
import com.shravan.frauddetection.service.engine.RuleEngineService;
import com.shravan.frauddetection.service.engine.RuleResult;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RuleEngineService ruleEngineService;
    private final RuleEvaluationRepository ruleEvaluationRepository;
    
    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            RuleEvaluationRepository ruleEvaluationRepository,
            RuleEngineService ruleEngineService) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.ruleEvaluationRepository = ruleEvaluationRepository;
        this.ruleEngineService = ruleEngineService;
    }


    public TransactionResponse processTransaction(TransactionRequest request) {

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                    new ResourceNotFoundException("Account not found"));

        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setMerchantName(request.getMerchantName());
        transaction.setMerchantCategory(request.getMerchantCategory());
        transaction.setIpAddress(request.getIpAddress());
        transaction.setCreatedAt(LocalDateTime.now());


        List<RuleResult> results =
                ruleEngineService.evaluateTransaction(transaction);


        int riskScore = results.stream()
                .mapToInt(RuleResult::getScore)
                .sum();


        if (riskScore >= 100) {
            transaction.setStatus(TransactionStatus.BLOCKED);
        }
        else if (riskScore >= 50) {
            transaction.setStatus(TransactionStatus.REVIEW);
        }
        else {
            transaction.setStatus(TransactionStatus.ALLOWED);
        }


        Transaction savedTransaction =
                transactionRepository.save(transaction);


        for (RuleResult result : results) {

            RuleEvaluation evaluation = new RuleEvaluation();

            evaluation.setTransaction(savedTransaction);
            evaluation.setRuleCode(result.getRuleCode());
            evaluation.setTriggered(result.isTriggered());
            evaluation.setScoreContribution(result.getScore());
            evaluation.setDetailMessage(result.getMessage());

            ruleEvaluationRepository.save(evaluation);
        }
        
        return new TransactionResponse(
                savedTransaction.getId(),
                riskScore,
                savedTransaction.getStatus().name(),
                "Transaction processed successfully"
        );
    }
}
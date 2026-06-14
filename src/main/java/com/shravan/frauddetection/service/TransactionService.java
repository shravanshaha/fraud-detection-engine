package com.shravan.frauddetection.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shravan.frauddetection.dto.TransactionRequest;
import com.shravan.frauddetection.dto.TransactionResponse;
import com.shravan.frauddetection.exception.ResourceNotFoundException;
import com.shravan.frauddetection.model.entity.Account;
import com.shravan.frauddetection.model.entity.RuleEvaluation;
import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.model.entity.TransactionStatus;
import com.shravan.frauddetection.repository.AccountRepository;
import com.shravan.frauddetection.repository.RuleEvaluationRepository;
import com.shravan.frauddetection.repository.TransactionRepository;
import com.shravan.frauddetection.service.engine.RuleEngineService;
import com.shravan.frauddetection.service.engine.RuleResult;

@Service
public class TransactionService {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionService.class);

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

        logger.info(
                "Processing transaction for account: {}, amount: {}",
                request.getAccountNumber(),
                request.getAmount()
        );


        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        logger.info(
                "Account found with ID: {}",
                account.getId()
        );


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


        logger.info(
                "Risk score calculated: {}",
                riskScore
        );


        if (riskScore >= 100) {

            transaction.setStatus(TransactionStatus.BLOCKED);

            logger.warn(
                    "Blocked transaction detected. Risk score: {}",
                    riskScore
            );

        } else if (riskScore >= 50) {

            transaction.setStatus(TransactionStatus.REVIEW);

            logger.warn(
                    "Transaction marked for review. Risk score: {}",
                    riskScore
            );

        } else {

            transaction.setStatus(TransactionStatus.ALLOWED);

            logger.info(
                    "Transaction allowed"
            );
        }


        Transaction savedTransaction =
                transactionRepository.save(transaction);


        logger.info(
                "Transaction saved successfully with ID: {}",
                savedTransaction.getId()
        );


        for (RuleResult result : results) {

            RuleEvaluation evaluation = new RuleEvaluation();

            evaluation.setTransaction(savedTransaction);
            evaluation.setRuleCode(result.getRuleCode());
            evaluation.setTriggered(result.isTriggered());
            evaluation.setScoreContribution(result.getScore());
            evaluation.setDetailMessage(result.getMessage());

            ruleEvaluationRepository.save(evaluation);
        }


        logger.info(
                "Saved {} rule evaluations for transaction ID: {}",
                results.size(),
                savedTransaction.getId()
        );


        return new TransactionResponse(
                savedTransaction.getId(),
                riskScore,
                savedTransaction.getStatus().name(),
                "Transaction processed successfully"
        );
    }
}
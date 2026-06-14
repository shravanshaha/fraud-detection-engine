package com.shravan.frauddetection.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shravan.frauddetection.repository.AccountRepository;
import com.shravan.frauddetection.repository.TransactionRepository;
import com.shravan.frauddetection.repository.RuleEvaluationRepository;
import com.shravan.frauddetection.service.engine.RuleEngineService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.shravan.frauddetection.dto.TransactionRequest;
import com.shravan.frauddetection.dto.TransactionResponse;
import com.shravan.frauddetection.model.entity.Account;
import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.model.entity.TransactionStatus;
import com.shravan.frauddetection.service.engine.RuleResult;

import com.shravan.frauddetection.exception.ResourceNotFoundException;

public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RuleEvaluationRepository ruleEvaluationRepository;

    @Mock
    private RuleEngineService ruleEngineService;


    @InjectMocks
    private TransactionService transactionService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void shouldProcessValidTransaction() {

        // Arrange

        TransactionRequest request = new TransactionRequest();

        request.setAccountNumber("ACC1001");
        request.setAmount(new BigDecimal("20000"));
        request.setCurrency("INR");
        request.setMerchantName("Amazon");
        request.setMerchantCategory("ONLINE");
        request.setIpAddress("192.168.1.100");


        Account account = new Account();

        account.setId(1L);
        account.setAccountNumber("ACC1001");


        RuleResult ruleResult = new RuleResult(
                "AMOUNT_DEVIATION",
                true,
                40,
                "Amount exceeds normal range"
        );


        Transaction savedTransaction = new Transaction();

        savedTransaction.setId(10L);
        savedTransaction.setStatus(TransactionStatus.ALLOWED);


        when(accountRepository.findByAccountNumber("ACC1001"))
                .thenReturn(Optional.of(account));

        when(ruleEngineService.evaluateTransaction(any(Transaction.class)))
                .thenReturn(List.of(ruleResult));

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);


        // Act

        TransactionResponse response =
                transactionService.processTransaction(request);


        // Assert

        assertNotNull(response);

        assertEquals(10L, response.getTransactionId());

        assertEquals(40, response.getRiskScore());

        assertEquals("ALLOWED", response.getDecision());
        
        verify(accountRepository, times(1))
        .findByAccountNumber("ACC1001");

verify(ruleEngineService, times(1))
        .evaluateTransaction(any(Transaction.class));

verify(transactionRepository, times(1))
        .save(any(Transaction.class));

verify(ruleEvaluationRepository, times(1))
.save(any());
    
    }
    
    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        // Arrange

        TransactionRequest request = new TransactionRequest();

        request.setAccountNumber("INVALID_ACCOUNT");


        when(accountRepository.findByAccountNumber("INVALID_ACCOUNT"))
                .thenReturn(Optional.empty());


        // Act + Assert

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> transactionService.processTransaction(request)
                );


        assertEquals(
                "Account not found",
                exception.getMessage()
        );
    }
    
    @Test
    void shouldBlockTransactionWhenRiskScoreIsHigh() {

        // Arrange

        TransactionRequest request = new TransactionRequest();

        request.setAccountNumber("ACC1001");
        request.setAmount(new BigDecimal("50000"));
        request.setCurrency("INR");
        request.setMerchantName("SCAM_STORE");
        request.setMerchantCategory("ONLINE");
        request.setIpAddress("192.168.1.100");


        Account account = new Account();

        account.setId(1L);
        account.setAccountNumber("ACC1001");


        RuleResult amountRule = new RuleResult(
                "AMOUNT_DEVIATION",
                true,
                40,
                "High amount"
        );


        RuleResult blacklistRule = new RuleResult(
                "BLACKLIST_CHECK",
                true,
                80,
                "Blacklisted merchant"
        );


        Transaction savedTransaction = new Transaction();

        savedTransaction.setId(20L);
        savedTransaction.setStatus(TransactionStatus.BLOCKED);


        when(accountRepository.findByAccountNumber("ACC1001"))
                .thenReturn(Optional.of(account));


        when(ruleEngineService.evaluateTransaction(any(Transaction.class)))
                .thenReturn(List.of(amountRule, blacklistRule));


        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);


        // Act

        TransactionResponse response =
                transactionService.processTransaction(request);


        // Assert

        assertEquals(120, response.getRiskScore());

        assertEquals(
                "BLOCKED",
                response.getDecision()
        );

        assertEquals(
                20L,
                response.getTransactionId()
        );
    }
    
    @Test
    void shouldMarkTransactionForReview() {

        // Arrange

        TransactionRequest request = new TransactionRequest();

        request.setAccountNumber("ACC1001");
        request.setAmount(new BigDecimal("25000"));
        request.setCurrency("INR");
        request.setMerchantName("Amazon");
        request.setMerchantCategory("ONLINE");
        request.setIpAddress("192.168.1.100");


        Account account = new Account();

        account.setId(1L);
        account.setAccountNumber("ACC1001");


        RuleResult ruleResult = new RuleResult(
                "BLACKLIST_CHECK",
                true,
                80,
                "Suspicious merchant"
        );


        Transaction savedTransaction = new Transaction();

        savedTransaction.setId(30L);
        savedTransaction.setStatus(TransactionStatus.REVIEW);


        when(accountRepository.findByAccountNumber("ACC1001"))
                .thenReturn(Optional.of(account));


        when(ruleEngineService.evaluateTransaction(any(Transaction.class)))
                .thenReturn(List.of(ruleResult));


        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);


        // Act

        TransactionResponse response =
                transactionService.processTransaction(request);


        // Assert

        assertEquals(80, response.getRiskScore());

        assertEquals(
                "REVIEW",
                response.getDecision()
        );

        assertEquals(
                30L,
                response.getTransactionId()
        );
    }
}
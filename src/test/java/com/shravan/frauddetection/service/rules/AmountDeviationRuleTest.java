package com.shravan.frauddetection.service.rules;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shravan.frauddetection.model.entity.Account;
import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.service.engine.RuleResult;
import com.shravan.frauddetection.service.rules.AmountDeviationRule;

public class AmountDeviationRuleTest {

    private AmountDeviationRule rule;

    @BeforeEach
    void setup() {
        rule = new AmountDeviationRule();
    }

    @Test
    void shouldTriggerFraudWhenAmountExceedsThreshold() {

        Account account = new Account();
        account.setAvgTransactionAmount(new BigDecimal("5000"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(new BigDecimal("20000"));

        RuleResult result = rule.evaluate(transaction);

        assertTrue(result.isTriggered());
        assertEquals(40, result.getScore());
        assertEquals(
                "AMOUNT_DEVIATION",
                result.getRuleCode()
        );
    }

    @Test
    void shouldNotTriggerFraudForNormalAmount() {

        Account account = new Account();
        account.setAvgTransactionAmount(new BigDecimal("5000"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(new BigDecimal("3000"));

        RuleResult result = rule.evaluate(transaction);

        assertFalse(result.isTriggered());
        assertEquals(0, result.getScore());
    }
}
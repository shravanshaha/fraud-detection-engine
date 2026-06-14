package com.shravan.frauddetection.service.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.service.engine.RuleResult;

public class BlacklistRuleTest {

    private BlacklistRule rule;

    @BeforeEach
    void setup() {
        rule = new BlacklistRule();
    }

    @Test
    void shouldTriggerWhenMerchantIsBlacklisted() {

        Transaction transaction = new Transaction();
        transaction.setMerchantName("SCAM_STORE");

        RuleResult result = rule.evaluate(transaction);

        assertTrue(result.isTriggered());
        assertEquals(80, result.getScore());        assertEquals("BLACKLIST_CHECK", result.getRuleCode());
    }

    @Test
    void shouldNotTriggerForSafeMerchant() {

        Transaction transaction = new Transaction();
        transaction.setMerchantName("Amazon");

        RuleResult result = rule.evaluate(transaction);

        assertFalse(result.isTriggered());
        assertEquals(0, result.getScore());
    }
}
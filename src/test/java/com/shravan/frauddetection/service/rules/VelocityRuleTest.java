package com.shravan.frauddetection.service.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.service.engine.RuleResult;

public class VelocityRuleTest {

    private VelocityRule rule;

    @BeforeEach
    void setup() {
        rule = new VelocityRule();
    }

    @Test
    void shouldNotTriggerVelocityForSingleTransaction() {

        Transaction transaction = new Transaction();

        RuleResult result = rule.evaluate(transaction);

        assertFalse(result.isTriggered());
        assertEquals(0, result.getScore());
        assertEquals("VELOCITY_CHECK", result.getRuleCode());
    }
}
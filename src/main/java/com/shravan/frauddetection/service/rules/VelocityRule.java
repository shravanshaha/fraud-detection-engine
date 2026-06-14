package com.shravan.frauddetection.service.rules;

import org.springframework.stereotype.Component;

import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.service.engine.FraudRule;
import com.shravan.frauddetection.service.engine.RuleResult;

@Component
public class VelocityRule implements FraudRule {

    private static final int RISK_SCORE = 60;

    @Override
    public RuleResult evaluate(Transaction transaction) {

        // Database transaction history check will be implemented later
        boolean highVelocity = false;

        if (highVelocity) {
        	return new RuleResult(
        	        getRuleCode(),
        	        true,
        	        RISK_SCORE,
        	        "Too many transactions in a short period"
        	);
        }

        return new RuleResult(
                getRuleCode(),
                false,
                0,
                "Transaction velocity is normal"
        );
        }

    @Override
    public String getRuleCode() {
        return "VELOCITY_CHECK";
    }
}
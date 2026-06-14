package com.shravan.frauddetection.service.rules;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.service.engine.FraudRule;
import com.shravan.frauddetection.service.engine.RuleResult;

@Component
public class AmountDeviationRule implements FraudRule {

    private static final BigDecimal MULTIPLIER =
            new BigDecimal("3.0");

    private static final int RISK_SCORE = 40;

    @Override
    public RuleResult evaluate(Transaction transaction) {

        BigDecimal average =
                transaction.getAccount()
                           .getAvgTransactionAmount();

        BigDecimal threshold =
                average.multiply(MULTIPLIER);

        boolean suspicious =
                transaction.getAmount()
                           .compareTo(threshold) > 0;

        if (suspicious) {
        	return new RuleResult(
        	        getRuleCode(),
        	        true,
        	        RISK_SCORE,
        	        "Transaction amount exceeds normal behavior"
        	);
        }

        return new RuleResult(
                getRuleCode(),
                false,
                0,
                "Amount is within expected range"
        );
    }

    @Override
    public String getRuleCode() {
        return "AMOUNT_DEVIATION";
    }
}
package com.shravan.frauddetection.service.rules;

import org.springframework.stereotype.Component;

import com.shravan.frauddetection.model.entity.Transaction;
import com.shravan.frauddetection.service.engine.FraudRule;
import com.shravan.frauddetection.service.engine.RuleResult;

@Component
public class BlacklistRule implements FraudRule {

    private static final int RISK_SCORE = 80;

    @Override
    public RuleResult evaluate(Transaction transaction) {

        String merchant = transaction.getMerchantName();

        boolean blacklisted =
                "SCAM_STORE".equalsIgnoreCase(merchant)
                || "FAKE_PAYMENT".equalsIgnoreCase(merchant);

        if (blacklisted) {
            return new RuleResult(
                    getRuleCode(),
                    true,
                    RISK_SCORE,
                    "Transaction matches blacklist"
            );
        }

        return new RuleResult(
                getRuleCode(),
                false,
                0,
                "No blacklist match found"
        );
    }

    @Override
    public String getRuleCode() {
        return "BLACKLIST_CHECK";
    }
}
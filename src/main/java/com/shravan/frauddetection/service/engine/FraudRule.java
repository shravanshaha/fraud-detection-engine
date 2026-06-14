package com.shravan.frauddetection.service.engine;

import com.shravan.frauddetection.model.entity.Transaction;

public interface FraudRule {

    RuleResult evaluate(Transaction transaction);

    String getRuleCode();
}
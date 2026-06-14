package com.shravan.frauddetection.service.engine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shravan.frauddetection.model.entity.Transaction;

@Service
public class RuleEngineService {

    private final List<FraudRule> fraudRules;

    public RuleEngineService(List<FraudRule> fraudRules) {
        this.fraudRules = fraudRules;
    }

    public List<RuleResult> evaluateTransaction(Transaction transaction) {

        return fraudRules.stream()
                .map(rule -> rule.evaluate(transaction))
                .filter(RuleResult::isTriggered)
                .toList();
    }
    
    public int calculateTotalScore(Transaction transaction) {

        return fraudRules.stream()
                .map(rule -> rule.evaluate(transaction))
                .filter(RuleResult::isTriggered)
                .mapToInt(RuleResult::getScore)
                .sum();
    }
}
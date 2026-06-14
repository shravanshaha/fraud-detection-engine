package com.shravan.frauddetection.service.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RuleResult {

    private String ruleCode;

    private boolean triggered;

    private int score;

    private String message;
}
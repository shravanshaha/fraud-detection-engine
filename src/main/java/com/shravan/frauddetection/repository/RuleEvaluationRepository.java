package com.shravan.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shravan.frauddetection.model.entity.RuleEvaluation;

public interface RuleEvaluationRepository extends JpaRepository<RuleEvaluation, Long> {

}
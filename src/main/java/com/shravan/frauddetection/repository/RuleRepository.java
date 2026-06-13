package com.shravan.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shravan.frauddetection.model.entity.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long> {

}
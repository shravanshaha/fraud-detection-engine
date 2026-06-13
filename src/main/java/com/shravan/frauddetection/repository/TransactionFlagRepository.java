package com.shravan.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shravan.frauddetection.model.entity.TransactionFlag;

public interface TransactionFlagRepository extends JpaRepository<TransactionFlag, Long> {

}
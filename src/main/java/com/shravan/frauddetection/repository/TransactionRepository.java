package com.shravan.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shravan.frauddetection.model.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
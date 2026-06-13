package com.shravan.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shravan.frauddetection.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
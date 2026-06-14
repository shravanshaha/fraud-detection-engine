package com.shravan.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shravan.frauddetection.model.entity.Account;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountNumber(String accountNumber);
}
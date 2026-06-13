package com.shravan.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shravan.frauddetection.model.entity.BlacklistEntry;

public interface BlacklistRepository extends JpaRepository<BlacklistEntry, Long> {

}
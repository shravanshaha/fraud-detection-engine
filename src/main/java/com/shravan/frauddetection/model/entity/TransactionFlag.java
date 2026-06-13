package com.shravan.frauddetection.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_flags")
public class TransactionFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(name = "total_risk_score", nullable = false)
    private Integer totalRiskScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlagDecision decision;

    @Enumerated(EnumType.STRING)
    @Column(name = "reviewer_status", nullable = false)
    private ReviewerStatus reviewerStatus;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "reviewed_by")
    private String reviewedBy;
}
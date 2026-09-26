package com.frauddetectionsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private AccountModel sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private AccountModel receiver;
    @Column(precision = 19, scale = 2)
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime transactionDate;
    private String transactionStatus;
    private String transactionId;
    @Column(unique = true)
    private String idempotencyKey;

    @PrePersist
    public void generateTransactionId() {
        if (this.transactionId == null) {
            this.transactionId = UUID.randomUUID().toString();
        }
    }
}
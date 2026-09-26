package com.frauddetectionsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "receiver_id" , nullable = false)
    private AccountModel receiver;
    private double amount;
    private String transactionType;
    private LocalDateTime transactionDate;
    private String transactionStatus;
    private String transactionId;
    @Column(unique = true)
    private String idempotencyKey;


}

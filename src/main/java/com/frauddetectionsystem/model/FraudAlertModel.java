package com.frauddetectionsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class FraudAlertModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "transaction_id",unique = true, nullable = false)
    private TransactionModel transaction;
    private LocalDateTime alertDate;
    private String alertStatus;
    private boolean isResolved = false;

}

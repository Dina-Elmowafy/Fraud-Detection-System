package com.frauddetectionsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String userName;
    private String password;
    private boolean active;
    private BigDecimal balance;

    public AccountModel(String accountNumber, String userName, String password, boolean active, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.balance = balance;
    }
}

package com.frauddetectionsystem.fraud;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;

public interface FraudRule {
    boolean isFraudulent(TransactionRequestDTO requestDTO);


    String getFraudReason();
}

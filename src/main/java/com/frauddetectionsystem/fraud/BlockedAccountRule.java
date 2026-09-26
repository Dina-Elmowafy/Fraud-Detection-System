package com.frauddetectionsystem.fraud;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class BlockedAccountRule implements FraudRule{
    @Override
    public boolean isFraudulent(TransactionRequestDTO requestDTO) {
        if("111".equals(requestDTO.getReceiverAccountNumber())){
            return true;
        }
     return false;
    }

    @Override
    public String getFraudReason() {
        return "This account has been permanently banned.";
    }
}

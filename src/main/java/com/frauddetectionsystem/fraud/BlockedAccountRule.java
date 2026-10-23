package com.frauddetectionsystem.fraud;

import org.springframework.stereotype.Component;

@Component
public class BlockedAccountRule implements FraudRule{
    @Override
    public boolean isFraudulent(FraudCheckContext context) {
        if("111".equals(context.getRequestDTO().getReceiverAccountNumber())){
            return true;
        }
     return false;
    }

    @Override
    public String getFraudReason() {
        return "This account has been permanently banned.";
    }
}

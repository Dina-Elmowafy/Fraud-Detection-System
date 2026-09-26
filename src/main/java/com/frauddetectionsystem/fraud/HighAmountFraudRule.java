package com.frauddetectionsystem.fraud;

import org.springframework.stereotype.Component;

@Component
public class HighAmountFraudRule implements FraudRule{
    @Override
    public boolean isFraudulent(FraudCheckContext context) {
        return context.getRequestDTO().getAmount().compareTo(new java.math.BigDecimal("10000")) > 0;
    }

    @Override
    public String getFraudReason() {
        return "Fraud Detected: Amount exceeds the maximum allowed limit of 10,000!";
    }
}

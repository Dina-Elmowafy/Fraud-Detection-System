package com.frauddetectionsystem.fraud;

public interface FraudRule {
    boolean isFraudulent(FraudCheckContext context);


    String getFraudReason();
}

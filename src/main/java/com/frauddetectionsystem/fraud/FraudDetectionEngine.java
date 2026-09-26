package com.frauddetectionsystem.fraud;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FraudDetectionEngine {
    private final List<FraudRule> rules;
    public String checkForFraud(FraudCheckContext context) {
        for(FraudRule rule: rules ){
            if(rule.isFraudulent(context)){
                return rule.getFraudReason();
            }
        }
        return null;
    }
}

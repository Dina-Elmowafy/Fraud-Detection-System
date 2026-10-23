package com.frauddetectionsystem.fraud;

import com.frauddetectionsystem.repo.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class VelocityRule implements FraudRule{
    private final TransactionRepo transactionRepo;

    @Override
    public boolean isFraudulent(FraudCheckContext context) {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        long recentTransactions = transactionRepo.countBySender_AccountNumberAndTransactionDateAfterAndTransactionStatus(
                context.getSenderAccountNumber(),fiveMinutesAgo,"SUCCESS");

        return recentTransactions >=3;
    }

    @Override
    public String getFraudReason() {
        return "VELOCITY_ALERT: A large number of transfers were detected within a very short time";
    }
}

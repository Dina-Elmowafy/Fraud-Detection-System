package com.frauddetectionsystem.fraud;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.repo.TransactionRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FraudRulesTest {
    @Test
    void highAmountRuleFiresOnlyAboveTheLimit() {
        HighAmountFraudRule rule = new HighAmountFraudRule();

        assertTrue(rule.isFraudulent(context("1002", "10000.01")));
        assertFalse(rule.isFraudulent(context("1002", "10000.00")));
    }

    @Test
    void blockedAccountRuleFiresForTheConfiguredDemoAccount() {
        BlockedAccountRule rule = new BlockedAccountRule();

        assertTrue(rule.isFraudulent(context("111", "25.00")));
        assertFalse(rule.isFraudulent(context("1002", "25.00")));
    }

    @Test
    void velocityRuleFiresAfterThreeRecentSuccessfulTransfers() {
        TransactionRepo transactionRepo = mock(TransactionRepo.class);
        when(transactionRepo.countBySender_AccountNumberAndTransactionDateAfterAndTransactionStatus(
                eq("1001"), any(LocalDateTime.class), eq("SUCCESS"))).thenReturn(3L);

        VelocityRule rule = new VelocityRule(transactionRepo);

        assertTrue(rule.isFraudulent(new FraudCheckContext("1001", request("1002", "25.00"))));
    }

    @Test
    void velocityRuleDoesNotFireBelowTheThreshold() {
        TransactionRepo transactionRepo = mock(TransactionRepo.class);
        when(transactionRepo.countBySender_AccountNumberAndTransactionDateAfterAndTransactionStatus(
                eq("1001"), any(LocalDateTime.class), eq("SUCCESS"))).thenReturn(2L);

        VelocityRule rule = new VelocityRule(transactionRepo);

        assertFalse(rule.isFraudulent(new FraudCheckContext("1001", request("1002", "25.00"))));
    }

    private static FraudCheckContext context(String receiver, String amount) {
        return new FraudCheckContext("1001", request(receiver, amount));
    }

    private static TransactionRequestDTO request(String receiver, String amount) {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setReceiverAccountNumber(receiver);
        request.setAmount(new BigDecimal(amount));
        return request;
    }
}

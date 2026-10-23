package com.frauddetectionsystem.repo;

import com.frauddetectionsystem.model.TransactionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<TransactionModel,Long> {

    Optional<TransactionModel>findByIdempotencyKeyAndSender (String idempotencyKey,String sender);
    Optional<TransactionModel> findByTransactionId(String transactionId);
    @Query("SELECT t FROM TransactionModel t WHERE t.sender.accountNumber = :accountNumber OR t.receiver.accountNumber = :accountNumber")
    Page<TransactionModel> findByAccount(@Param("accountNumber") String accountNumber, Pageable pageable);
    long countBySender_AccountNumberAndTransactionDateAfterAndTransactionStatus(String accountNumber, LocalDateTime timeLimit, String status);
}

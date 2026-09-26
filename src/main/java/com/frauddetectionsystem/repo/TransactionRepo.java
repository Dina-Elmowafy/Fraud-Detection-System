package com.frauddetectionsystem.repo;

import com.frauddetectionsystem.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepo extends JpaRepository<TransactionModel,Long> {

    Optional<TransactionModel>findByIdempotencyKey (String idempotencyKey);
}

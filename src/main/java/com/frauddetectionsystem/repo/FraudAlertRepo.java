package com.frauddetectionsystem.repo;

import com.frauddetectionsystem.model.FraudAlertModel;
import com.frauddetectionsystem.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudAlertRepo extends JpaRepository<FraudAlertModel, Long> {

}

package com.frauddetectionsystem.repo;

import com.frauddetectionsystem.model.AccountModel;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepo extends JpaRepository<AccountModel,Long> {

   boolean existsByAccountNumber(String accountNumber);
   @Lock(LockModeType.PESSIMISTIC_WRITE)
   @Query("SELECT a FROM AccountModel a WHERE a.accountNumber = :accountNumber")
   Optional<AccountModel> findForUpdateByAccountNumber(String accountNumber);


}

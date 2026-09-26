package com.frauddetectionsystem.repo;

import com.frauddetectionsystem.model.AccountModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountModel,Long> {

   Optional<AccountModel> findByAccountNumber(String accountNumber);
}

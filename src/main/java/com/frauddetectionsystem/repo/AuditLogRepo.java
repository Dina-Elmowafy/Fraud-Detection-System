package com.frauddetectionsystem.repo;

import com.frauddetectionsystem.model.AuditLogModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuditLogRepo extends JpaRepository<AuditLogModel, Long> {
}

package com.frauddetectionsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
@Immutable
@Entity
@Data
public class AuditLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(updatable = false)
    private String actionType;
    @Column(updatable = false)
    private String actionDetails;
    @Column(updatable = false)
    private LocalDateTime actionDate;
    @Column(updatable = false)
    private String transactionId;
}

package com.frauddetectionsystem.DTO;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class TransactionResponseDTO {
    private Long id;
    private String transactionId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionStatus;
    private String receiver;
    private String sender;
}

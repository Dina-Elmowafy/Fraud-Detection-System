package com.frauddetectionsystem.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {
    @NotBlank(message = "Receiver account number cannot be blank")
  private String receiverAccountNumber;
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    private String idempotencyKey;
}

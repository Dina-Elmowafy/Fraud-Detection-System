package com.frauddetectionsystem.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDTO {
    private String accountNumber;
    private String userName;
    private BigDecimal balance;
}

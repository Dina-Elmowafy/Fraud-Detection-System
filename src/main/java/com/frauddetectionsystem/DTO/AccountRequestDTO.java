package com.frauddetectionsystem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {
    @NotBlank(message = "Account number cannot be blank")
    private String accountNumber;
    @NotBlank(message = "userName is required")
    private String userName;
    @NotBlank(message = "Password is required")
    @Size(min =6,message = "Password must be at least 6 characters long")
    private String password;
    @Min(value = 0, message = "Balance cannot be negative")
    private BigDecimal balance;
}

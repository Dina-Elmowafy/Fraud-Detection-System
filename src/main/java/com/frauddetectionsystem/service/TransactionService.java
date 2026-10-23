package com.frauddetectionsystem.service;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.DTO.TransactionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionResponseDTO processTransaction(String senderAccountNumber, TransactionRequestDTO requestDTO);
     Page<TransactionResponseDTO> getAllTransactions (Pageable pageable);
     TransactionResponseDTO getTransactionById(String transactionId);
    Page<TransactionResponseDTO> getTransactionsByAccount(String accountNumber, Pageable pageable);
}

package com.frauddetectionsystem.Service;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.DTO.TransactionResponseDTO;
import com.frauddetectionsystem.model.TransactionModel;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionModel processTransaction(String senderAccountNumber, TransactionRequestDTO requestDTO);
     Page<TransactionResponseDTO> getAllTransactions (Pageable pageable);

}

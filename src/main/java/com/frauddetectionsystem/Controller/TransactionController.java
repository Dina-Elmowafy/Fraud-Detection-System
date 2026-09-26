package com.frauddetectionsystem.Controller;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.DTO.TransactionResponseDTO;
import com.frauddetectionsystem.Service.TransactionService;
import com.frauddetectionsystem.mapper.TransactionMapper;
import com.frauddetectionsystem.model.TransactionModel;
import com.frauddetectionsystem.repo.TransactionRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {


    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/{senderAccountNumber}/process")
    public TransactionResponseDTO processTransaction
            ( @PathVariable String senderAccountNumber,@Valid  @RequestBody TransactionRequestDTO requestDTO) {
           TransactionModel savedTransaction = transactionService.processTransaction(senderAccountNumber, requestDTO);
            return transactionMapper.toDto(savedTransaction);

    }
    @GetMapping
    public Page<TransactionResponseDTO> getAllTransactions(Pageable pageable) {
        return transactionService.getAllTransactions(pageable);
    }




}

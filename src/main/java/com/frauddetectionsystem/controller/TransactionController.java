package com.frauddetectionsystem.controller;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.DTO.TransactionResponseDTO;
import com.frauddetectionsystem.service.TransactionService;
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


    @PostMapping("/{senderAccountNumber}/process")
    public TransactionResponseDTO processTransaction
            ( @PathVariable String senderAccountNumber,@Valid  @RequestBody TransactionRequestDTO requestDTO) {
           return transactionService.processTransaction(senderAccountNumber, requestDTO);

    }
    @GetMapping
    public Page<TransactionResponseDTO> getAllTransactions(Pageable pageable) {
        return transactionService.getAllTransactions(pageable);
    }
    @GetMapping("{transactionId}")
    public TransactionResponseDTO getTransactionById(@PathVariable("transactionId") String transactionId) {
        return  transactionService.getTransactionById(transactionId);
    }
    @GetMapping("/account/{accountNumber}")
    public Page<TransactionResponseDTO> getTransactionsByAccount(@PathVariable("accountNumber") String accountNumber, Pageable pageable){
        return transactionService.getTransactionsByAccount(accountNumber, pageable);
    }

}

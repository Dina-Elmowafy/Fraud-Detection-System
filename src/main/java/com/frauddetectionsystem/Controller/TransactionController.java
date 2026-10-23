package com.frauddetectionsystem.Controller;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.DTO.TransactionResponseDTO;
import com.frauddetectionsystem.Service.TransactionService;
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
    @GetMapping("{id}")
    public TransactionResponseDTO getTransactionById(@PathVariable String id) {
        return  transactionService.getTransactionById(id);
    }
    @GetMapping("/account/{accountNumber}")
    public Page<TransactionResponseDTO> getTransactionsByAccount(@PathVariable("accountNumber") String accountNumber, Pageable pageable){
        return transactionService.getTransactionsByAccount(accountNumber, pageable);
    }

}

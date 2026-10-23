package com.frauddetectionsystem.service.impl;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.DTO.TransactionResponseDTO;
import com.frauddetectionsystem.exception.InsufficientFundsException;
import com.frauddetectionsystem.service.TransactionService;
import com.frauddetectionsystem.exception.FraudDetectedException;
import com.frauddetectionsystem.fraud.FraudCheckContext;
import com.frauddetectionsystem.fraud.FraudDetectionEngine;
import com.frauddetectionsystem.mapper.TransactionMapper;
import com.frauddetectionsystem.model.AccountModel;
import com.frauddetectionsystem.model.AuditLogModel;
import com.frauddetectionsystem.model.FraudAlertModel;
import com.frauddetectionsystem.model.TransactionModel;
import com.frauddetectionsystem.repo.AccountRepo;
import com.frauddetectionsystem.repo.AuditLogRepo;
import com.frauddetectionsystem.repo.FraudAlertRepo;
import com.frauddetectionsystem.repo.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TransactionServiceImpl implements TransactionService {
    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;
    private final FraudAlertRepo fraudAlertRepo;
    private final TransactionMapper transactionMapper;
    private final FraudDetectionEngine fraudDetectionEngine;
    private final AuditLogRepo auditLogRepo;

    @Override
    @Transactional(noRollbackFor = FraudDetectedException.class)
    public TransactionResponseDTO processTransaction(String senderAccountNumber, TransactionRequestDTO requestDTO) {
        if(requestDTO.getIdempotencyKey() != null){
            Optional<TransactionModel>existingTransaction=transactionRepo.findByIdempotencyKey(requestDTO.getIdempotencyKey());
            if(existingTransaction.isPresent()){
                return transactionMapper.toDto(existingTransaction.get());
            }
        }

        AccountModel senderAccount = accountRepo.findForUpdateByAccountNumber(senderAccountNumber)
                .orElseThrow(() -> new RuntimeException("Sender account not found!"));

        AccountModel receiverAccount = accountRepo.findForUpdateByAccountNumber(requestDTO.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));
        if (senderAccountNumber.equals(requestDTO.getReceiverAccountNumber())) {
            throw new RuntimeException("Sender and receiver accounts cannot be the same");
        }
        if (!senderAccount.isActive()) {
            throw new RuntimeException("Sender account is inactive and cannot perform transactions");
        }
        if (senderAccount.getBalance().compareTo(requestDTO.getAmount())<0 ) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        FraudCheckContext context = new FraudCheckContext(senderAccountNumber, requestDTO);
        String fraudRule =fraudDetectionEngine.checkForFraud(context);
        if (fraudRule!=null) {

            TransactionModel transaction = transactionMapper.toEntity(requestDTO);
            transaction.setTransactionStatus("FRAUD_SUSPECTED");
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setSender(senderAccount);
            transaction.setReceiver(receiverAccount);


            TransactionModel savedTransaction = transactionRepo.save(transaction);

            FraudAlertModel alert = new FraudAlertModel();
            alert.setAlertStatus("FRAUD_DETECTED");
            alert.setAlertDate(LocalDateTime.now());
            alert.setTransaction(savedTransaction);
            fraudAlertRepo.save(alert);

            AuditLogModel auditLog = new AuditLogModel();
            auditLog.setActionType("FRAUD_DETECTED");
            auditLog.setActionDetails("Blocked transfer from " +senderAccountNumber +" to "+
                    requestDTO.getReceiverAccountNumber() +"Reason"+fraudRule);
            auditLog.setActionDate(LocalDateTime.now());
            auditLog.setTransactionId(savedTransaction.getTransactionId());
            auditLogRepo.save(auditLog);

            throw new FraudDetectedException(fraudRule);
        }
        senderAccount.setBalance(senderAccount.getBalance().subtract(requestDTO.getAmount()) );
        receiverAccount.setBalance(receiverAccount.getBalance().add(requestDTO.getAmount()));

        TransactionModel transaction = transactionMapper.toEntity(requestDTO);
        transaction.setTransactionStatus("SUCCESS");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setSender(senderAccount);
        transaction.setReceiver(receiverAccount);
        accountRepo.save(senderAccount);
        accountRepo.save(receiverAccount);
        TransactionModel savedTransaction = transactionRepo.save(transaction);

        AuditLogModel successLog = new AuditLogModel();
        successLog.setActionType("TRANSFER_SUCCESS");
        successLog.setActionDetails("Successful transfer from " +requestDTO.getAmount() +" from " +
               senderAccountNumber +" to "+requestDTO.getReceiverAccountNumber());
        successLog.setActionDate(LocalDateTime.now());
        successLog.setTransactionId(savedTransaction.getTransactionId());
        auditLogRepo.save(successLog);

        return transactionMapper.toDto(savedTransaction);
    }


    @Override
    public Page<TransactionResponseDTO> getAllTransactions(Pageable pageable) {
        Page <TransactionModel> transactionModelPage =transactionRepo.findAll(pageable);
        return transactionModelPage.map(transactionMapper::toDto);
    }

    @Override
    public TransactionResponseDTO getTransactionById(String transactionId) {
        return transactionRepo.findByTransactionId(transactionId).
                map(transactionMapper::toDto).
                orElseThrow(() -> new RuntimeException("Transaction not found!"));
    }

    @Override
    public Page<TransactionResponseDTO> getTransactionsByAccount(String accountNumber, Pageable pageable) {
        return transactionRepo.findByAccount(accountNumber,pageable)
                .map(transactionMapper::toDto);
    }
}

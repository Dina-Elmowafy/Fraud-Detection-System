package com.frauddetectionsystem.mapper;

import com.frauddetectionsystem.DTO.TransactionRequestDTO;
import com.frauddetectionsystem.DTO.TransactionResponseDTO;
import com.frauddetectionsystem.model.TransactionModel;
import jakarta.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionDate", ignore = true)
    @Mapping(target = "transactionStatus" , ignore =true)
    TransactionModel toEntity(TransactionRequestDTO requestDTO);
    @Mapping(source = "sender.accountNumber", target = "sender")
    @Mapping(source = "receiver.accountNumber", target = "receiver")
    TransactionResponseDTO toDto(TransactionModel transactionModel);


}

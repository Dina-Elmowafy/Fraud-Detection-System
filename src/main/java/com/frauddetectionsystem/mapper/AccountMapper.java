package com.frauddetectionsystem.mapper;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.DTO.AccountResponseDTO;
import com.frauddetectionsystem.model.AccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface AccountMapper {
        @Mapping(target ="id" , ignore = true)
        @Mapping(target ="isactive" , ignore = true)

        AccountModel toEntity(AccountRequestDTO requestDTO);
        AccountResponseDTO toResponseDto(AccountModel accountModel);
}

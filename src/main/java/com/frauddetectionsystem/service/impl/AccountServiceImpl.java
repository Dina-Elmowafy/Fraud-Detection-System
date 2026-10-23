package com.frauddetectionsystem.service.impl;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.DTO.AccountResponseDTO;
import com.frauddetectionsystem.service.AccountService;
import com.frauddetectionsystem.mapper.AccountMapper;
import com.frauddetectionsystem.model.AccountModel;
import com.frauddetectionsystem.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponseDTO addAccount(AccountRequestDTO requestDTO) {

        if (accountRepo.existsByAccountNumber(requestDTO.getAccountNumber())) {

            throw new RuntimeException("Account number already exists!");
        }


        AccountModel newAccount = accountMapper.toEntity(requestDTO);
        newAccount.setActive(true);

        AccountModel savedAccount = accountRepo.save(newAccount);


        return accountMapper.toResponseDto(savedAccount);
    }
}
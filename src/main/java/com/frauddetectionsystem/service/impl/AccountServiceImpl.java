package com.frauddetectionsystem.service.impl;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.service.AccountService;
import com.frauddetectionsystem.mapper.AccountMapper;
import com.frauddetectionsystem.model.AccountModel;
import com.frauddetectionsystem.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;
    @Override
    public boolean isAccountNumberExists(String accountNumber) {
        Optional <AccountModel> existingAccount = accountRepo.findByAccountNumber(accountNumber);
        if(existingAccount.isPresent()){
            throw new RuntimeException("Account number already exists!");
        }
        return false;
    }

    @Override
    public AccountModel addAccount(AccountRequestDTO requestDTO) {
        isAccountNumberExists(requestDTO.getAccountNumber());
        AccountModel  newAccount= accountMapper.toEntity(requestDTO);
        newAccount.setActive(true);
         return accountRepo.save(newAccount);
    }

}

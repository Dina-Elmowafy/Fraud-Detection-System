package com.frauddetectionsystem.service;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.model.AccountModel;

public interface AccountService {

    AccountModel addAccount(AccountRequestDTO requestDTO);
    boolean isAccountNumberExists(String accountNumber);

}

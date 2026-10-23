package com.frauddetectionsystem.service;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.DTO.AccountResponseDTO;
import com.frauddetectionsystem.model.AccountModel;

public interface AccountService {

    AccountResponseDTO addAccount(AccountRequestDTO requestDTO);
   // boolean isAccountNumberExists(String accountNumber);

}

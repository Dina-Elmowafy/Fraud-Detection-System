package com.frauddetectionsystem.Service;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.model.AccountModel;

public interface AccountService {

    AccountModel addAccount(AccountRequestDTO requestDTO);
    boolean isAccountNumberExists(String accountNumber);

}

package com.frauddetectionsystem.controller;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.DTO.AccountResponseDTO;
import com.frauddetectionsystem.service.AccountService;
import com.frauddetectionsystem.mapper.AccountMapper;
import com.frauddetectionsystem.model.AccountModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/add")
    public AccountResponseDTO createAccount(@Valid @RequestBody AccountRequestDTO requestDTO) {
        AccountModel savedAccount = accountService.addAccount(requestDTO);
        return accountMapper.toResponseDto(savedAccount);
    }
}

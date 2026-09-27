package com.frauddetectionsystem.controller;

import com.frauddetectionsystem.DTO.AccountRequestDTO;
import com.frauddetectionsystem.DTO.AccountResponseDTO;
import com.frauddetectionsystem.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createAccount(@Valid @RequestBody AccountRequestDTO requestDTO) {

        return accountService.addAccount(requestDTO);
    }
}
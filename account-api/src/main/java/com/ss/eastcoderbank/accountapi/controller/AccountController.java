package com.ss.eastcoderbank.accountapi.controller;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.accountapi.service.AccountService;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody CreateUserAccountDto account) {
        accountService.createAccount(account);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<AccountDto>> getAccountByUser(@PathVariable Integer id) {
        return new ResponseEntity<>(accountService.getAccountsByUser(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer id) {
        accountService.deactivateAccount(id);
        return new ResponseEntity<>("Account deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Integer id) {
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto, @PathVariable Integer id) {
        accountService.updateAccount(updateAccountDto, id);
        return new ResponseEntity<>("Account updated", HttpStatus.PARTIAL_CONTENT);
    }
}

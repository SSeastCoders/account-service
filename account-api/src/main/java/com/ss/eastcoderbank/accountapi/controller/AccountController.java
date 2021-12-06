package com.ss.eastcoderbank.accountapi.controller;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.accountapi.service.AccountOptions;
import com.ss.eastcoderbank.accountapi.service.AccountService;
import com.ss.eastcoderbank.core.model.account.AccountType;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
    public ResponseEntity<Page<AccountDto>> getAllAccounts(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) boolean asc,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "") String nickname,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false) LocalDate fromDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) Float fromAmount,
            @RequestParam(required = false) Float toAmount,
            @RequestParam(required = false) AccountType type,
            @RequestParam(required = false) Boolean status
    ) {
        AccountOptions options = new AccountOptions(nickname, fromDate, toDate, fromAmount, toAmount, type, status);
        return new ResponseEntity<>(accountService.getAccountsByFilter(page, size, asc, sort, options), HttpStatus.OK);
    }

    @GetMapping("/accountsPage")
    public ResponseEntity<Page<AccountDto>> getPaginatedAccounts(@RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize, @RequestParam(value="asc", required = false) boolean asc, @RequestParam(value = "sort", required = false) String sort, Pageable page) {
        return new ResponseEntity<>(accountService.getAccounts(pageNumber, pageSize, asc, sort), HttpStatus.OK);
        //return accountService.getPaginatedAccounts(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public void updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto, @PathVariable Integer id) {
        accountService.updateAccount(updateAccountDto, id);
    }

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck() {}
}

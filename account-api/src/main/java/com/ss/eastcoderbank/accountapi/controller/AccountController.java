package com.ss.eastcoderbank.accountapi.controller;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.accountapi.service.AccountService;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody CreateUserAccountDto account) {
        log.trace("POST account endpoint reached...");
        accountService.createAccount(account);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<AccountDto>> getAccountByUser(@PathVariable Integer id) {
        log.trace("Get account/users/id endpoint reached...");
        log.debug("Endpoint id: {}", id);
        return new ResponseEntity<>(accountService.getAccountsByUser(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer id) {
        log.trace("DELETE account/id endpoint reached...");
        log.debug("Endpoint id: {}", id);
        accountService.deactivateAccount(id);
        return new ResponseEntity<>("Account deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Integer id) {
        log.trace("GET account/id endpoint reached...");
        log.debug("Endpoint id: {}", id);
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        log.trace("GET account endpoint reached, not paginated...");
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @GetMapping("/accountsPage")
    public ResponseEntity<Page<AccountDto>> getPaginatedAccounts(@RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize, @RequestParam(value="asc", required = false) boolean asc, @RequestParam(value = "sort", required = false) String sort, Pageable page) {
        log.trace("get account endpoint reached...");
        log.debug("Endpoint pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        return new ResponseEntity<>(accountService.getAccounts(pageNumber, pageSize, asc, sort), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public void updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto, @PathVariable Integer id) {
        log.trace("PUT account endpoint reached...");
        log.debug("Endpoint id: {}", id);
        accountService.updateAccount(updateAccountDto, id);
    }

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck() {
        log.trace("HEALTH account endpoint reached...");
    }
}

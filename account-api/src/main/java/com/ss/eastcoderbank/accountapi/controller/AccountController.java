package com.ss.eastcoderbank.accountapi.controller;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.accountapi.service.AccountService;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;
    private MeterRegistry meterRegistry;
    private Counter serviceCounter;
    private Timer serviceTimer;

    AccountController(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        serviceCounter = meterRegistry.counter("PAGE_VIEWS.Accounts");
        serviceTimer = meterRegistry.timer("execution.time.Accounts");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody CreateUserAccountDto account) {
        long startTime = System.currentTimeMillis();
        log.trace("POST account endpoint reached...");
        serviceCounter.increment();
        accountService.createAccount(account);
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<AccountDto>> getAccountByUser(@PathVariable Integer id) {
        long startTime = System.currentTimeMillis();
        log.trace("Get account/users/id endpoint reached...");
        log.debug("Endpoint id: {}", id);
        serviceCounter.increment();
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
        return new ResponseEntity<>(accountService.getAccountsByUser(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer id) {
        long startTime = System.currentTimeMillis();
        log.trace("DELETE account/id endpoint reached...");
        log.debug("Endpoint id: {}", id);
        accountService.deactivateAccount(id);
        serviceCounter.increment();
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
        return new ResponseEntity<>("Account deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Integer id) {
        long startTime = System.currentTimeMillis();
        log.trace("GET account/id endpoint reached...");
        log.debug("Endpoint id: {}", id);
        serviceCounter.increment();
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        long startTime = System.currentTimeMillis();
        log.trace("GET account endpoint reached, not paginated...");
        serviceCounter.increment();
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @GetMapping("/accountsPage")
    public ResponseEntity<Page<AccountDto>> getPaginatedAccounts(@RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize, @RequestParam(value="asc", required = false) boolean asc, @RequestParam(value = "sort", required = false) String sort, Pageable page) {
        long startTime = System.currentTimeMillis();
        log.trace("get account endpoint reached...");
        log.debug("Endpoint pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        serviceCounter.increment();
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
        return new ResponseEntity<>(accountService.getAccounts(pageNumber, pageSize, asc, sort), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public void updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto, @PathVariable Integer id) {
        long startTime = System.currentTimeMillis();
        log.trace("PUT account endpoint reached...");
        log.debug("Endpoint id: {}", id);
        serviceCounter.increment();
        accountService.updateAccount(updateAccountDto, id);
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
    }

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck() {
        long startTime = System.currentTimeMillis();
        serviceCounter.increment();
        log.trace("HEALTH account endpoint reached...");
        serviceTimer.record(Duration.ofMillis((System.currentTimeMillis()-startTime)));
    }
}

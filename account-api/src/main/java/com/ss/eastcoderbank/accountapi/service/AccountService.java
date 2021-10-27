package com.ss.eastcoderbank.accountapi.service;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.accountapi.mapper.UpdateAccountMapper;
import com.ss.eastcoderbank.accountapi.mapper.UserAccCreateMapper;
import com.ss.eastcoderbank.core.exeception.AccountNotEmptyException;
import com.ss.eastcoderbank.core.exeception.AccountNotFoundException;
import com.ss.eastcoderbank.core.exeception.UserNotFoundException;
import com.ss.eastcoderbank.core.model.account.Account;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.repository.AccountRepository;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import com.ss.eastcoderbank.core.transfermapper.AccountMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserAccCreateMapper accountCreateMapper;
    private final AccountMapper accountMapper;
    private UpdateAccountMapper updateAccountMapper;


    public void createAccount(CreateUserAccountDto payload) {

        log.info("Request for new account...");

        log.info("Mapping request DTO to account object...");
        Account account = accountCreateMapper.mapToEntity(payload);

        log.info("Transforming user ids into user list...");
        List<User> users = new ArrayList<>();
        payload.getUsersIds().forEach(integer -> users.add(userRepository.findById(integer).orElseThrow(UserNotFoundException::new)));

        log.info("Applying default account setting to new account...");
        account.setActiveStatus(true);
        if (account.getBalance() < 0) {
            account.setBalance(0F);
        }
        account.setInterestRate(.01);
        account.setOpenDate(LocalDate.now());
        account.setUsers(users);

        log.info("Saving new account to database...");
        accountRepository.save(account);

    }

    public List<AccountDto> getAccountsByUser(Integer id) {
        log.info("Request for accounts by user id: {}...", id);
        return accountRepository.findAccountByUsersId(id).stream().map(accountMapper::mapToDto).collect(Collectors.toList());
    }

    public Integer deactivateAccount(Integer id) {
        log.info("Request for deactivating account...");
        log.trace("Verifying account of id: {} exists...", id);
        Optional<Account> deactivatedAccount = accountRepository.findById(id);

        Account account = deactivatedAccount.orElseThrow(AccountNotFoundException::new);
        if (!account.getActiveStatus()) throw new AccountNotFoundException();
        if (account.getBalance()!=0) throw new AccountNotEmptyException();

        log.info("Deactivating account...");
        account.setActiveStatus(false);
        accountRepository.save(account);
        log.info("Returning deactivated account id: {}", id);
        return account.getId();
    }

    public Integer updateAccount(UpdateAccountDto updateAccountDto, Integer id) {
        log.info("Request for updating account id: {}...", id);
        try {
            log.trace("Verifying account of id: {} exists...", id);
            Account account = accountRepository.getById(id);
            updateAccountMapper.updateEntity(updateAccountDto, account);
            List<User> users = new ArrayList<>();
            try {
                log.trace("Verifying all users exist...");

                updateAccountDto.getUsersIds().forEach(integer -> users.add(userRepository.findById(integer).orElseThrow(UserNotFoundException::new)));
                log.trace("Assigning users to account...");
                account.setUsers(users);
            } catch (NullPointerException e) {
                log.warn("User not found...");
                log.trace(e.getMessage());
                throw new UserNotFoundException();
                //no users provided in update
            }
            log.info("Saving updated account information to database...");
            accountRepository.save(account);
            log.info("Returning id of updated account...");
            return account.getId();
        } catch (EntityNotFoundException e) {
            log.warn("Account not found...");
            log.trace(e.getMessage());
            throw new AccountNotFoundException();
        } catch (DataIntegrityViolationException dive) {
            log.warn("Account unable to be updated, data integrity violation...");
            log.trace(dive.getMessage());
            Throwable thr = dive.getCause();
            log.info(dive.getMessage(), dive);
            throw dive;
        }
    }

    public List<AccountDto> getAccounts() {
        log.info("Finding all accounts, not paginated");
        return accountRepository.findAll().stream().map(accountMapper::mapToDto).collect(Collectors.toList());
    }

    public AccountDto getAccountById(Integer id) {
        log.info("Finding account by ID");
        return accountMapper.mapToDto(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
    }

    public Page<AccountDto> getAccounts(Integer pageNumber, Integer pageSize, boolean asc, String sort) {
        log.info("Request for paginated results of all accounts...");
        Page<AccountDto> req;
        if (sort != null) {
            log.info("Results sorted by {}...", sort);
            req = accountRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sort))).map(account -> accountMapper.mapToDto(account));
        } else {
            log.info("Results not sorted...");
            req = accountRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(account -> accountMapper.mapToDto(account));
        }
        log.info("Returning Page of AccountDto...");
        return req;
    }
}

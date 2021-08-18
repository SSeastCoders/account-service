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
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
        Account account = accountCreateMapper.mapToEntity(payload);

        List<User> users = new ArrayList<>();
        payload.getUsersIds().forEach(integer -> users.add(userRepository.findById(integer).orElseThrow(UserNotFoundException::new)));
        account.setActiveStatus(true);
        if (account.getBalance() < 0) {
            account.setBalance(0F);
        }
        account.setInterestRate(.01);
        account.setOpenDate(LocalDate.now());
        account.setUsers(users);

        accountRepository.save(account);

    }

    public List<AccountDto> getAccountsByUser(Integer id) {
        return accountRepository.findAccountByUsersId(id).stream().map(accountMapper::mapToDto).collect(Collectors.toList());
    }
    public Integer deactivateAccount(Integer id) {

        Optional<Account> deactivatedAccount = accountRepository.findById(id);

        Account account = deactivatedAccount.orElseThrow(AccountNotFoundException::new);
        if (!account.getActiveStatus()) throw new AccountNotFoundException();
        if (account.getBalance()!=0) throw new AccountNotEmptyException();
        account.setActiveStatus(false);
        accountRepository.save(account);
        return account.getId();
    }

    public Integer updateAccount(UpdateAccountDto updateAccountDto, Integer id) {
        try {
            Account account = accountRepository.getById(id);
            updateAccountMapper.updateEntity(updateAccountDto, account);
            List<User> users = new ArrayList<>();
            updateAccountDto.getUsersIds().forEach(integer -> users.add(userRepository.findById(integer).orElseThrow(UserNotFoundException::new)));
            account.setUsers(users);
            accountRepository.save(account);
            return account.getId();
        } catch (EntityNotFoundException e) {
            throw new AccountNotFoundException();
        } catch (DataIntegrityViolationException dive) {
            Throwable thr = dive.getCause();
            log.info(dive.getMessage(), dive);
            throw dive;
        }
    }

    public List<AccountDto> getAccounts() {
        return accountRepository.findAll().stream().map(accountMapper::mapToDto).collect(Collectors.toList());
    }

    public AccountDto getAccountById(Integer id) {
        return accountMapper.mapToDto(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
    }
}

package com.ss.eastcoderbank.accountapi.service;

import com.ss.eastcoderbank.accountapi.mapper.AccountMapper;
import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.mapper.UserAccCreateMapper;
import com.ss.eastcoderbank.core.exeception.UserNotFoundException;
import com.ss.eastcoderbank.core.model.account.Account;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.repository.AccountRepository;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserAccCreateMapper accountCreateMapper;
    private final AccountMapper accountMapper;

    public void createAccount(CreateUserAccountDto payload) {
        Account account = accountCreateMapper.mapToEntity(payload);

        List<User> users = new ArrayList<>();
        payload.getUsersIds().forEach(integer -> users.add(userRepository.findById(integer).orElseThrow(UserNotFoundException::new)));
        account.setActiveStatus(true);
        account.setBalance(0F);
        account.setInterestRate(.01);
        account.setOpenDate(LocalDate.now());
        account.setUsers(users);

        accountRepository.save(account);

    }

    public List<AccountDto> getAccountsByUser(Integer id) {
        return accountRepository.findAccountByUser(id).stream().map(accountMapper::mapToDto).collect(Collectors.toList());
    }
}

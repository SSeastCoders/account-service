package com.ss.eastcoderbank.accountapi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.mapper.UpdateAccountMapper;
import com.ss.eastcoderbank.accountapi.mapper.UserAccCreateMapper;
import com.ss.eastcoderbank.core.exeception.AccountNotEmptyException;
import com.ss.eastcoderbank.core.model.account.Account;
import com.ss.eastcoderbank.core.model.account.AccountType;
import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.AccountRepository;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.transfermapper.AccountMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountService.class})
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
    @MockBean
    private AccountMapper accountMapper;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @MockBean
    private UpdateAccountMapper updateAccountMapper;

    @MockBean
    private UserAccCreateMapper userAccCreateMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateAccount() {
        when(this.userAccCreateMapper.mapToEntity((CreateUserAccountDto) any()))
                .thenThrow(new AccountNotEmptyException("An error occurred"));
        assertThrows(AccountNotEmptyException.class, () -> this.accountService.createAccount(new CreateUserAccountDto()));
        verify(this.userAccCreateMapper).mapToEntity((CreateUserAccountDto) any());
    }

    @Test
    public void testCreateAccount2() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        when(this.userAccCreateMapper.mapToEntity((CreateUserAccountDto) any())).thenReturn(account);

        Account account1 = new Account();
        account1.setNickName("Nick Name");
        account1.setUsers(new ArrayList<User>());
        account1.setId(1);
        account1.setInterestRate(10.0);
        account1.setActiveStatus(true);
        account1.setOpenDate(LocalDate.ofEpochDay(1L));
        account1.setAccountType(AccountType.CHECKING);
        account1.setBalance(10.0f);
        when(this.accountRepository.save((Account) any())).thenReturn(account1);
        this.accountService
                .createAccount(new CreateUserAccountDto(AccountType.CHECKING, 10.0f, new ArrayList<Integer>(), "Nick Name"));
        verify(this.userAccCreateMapper).mapToEntity((CreateUserAccountDto) any());
        verify(this.accountRepository).save((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testCreateAccount3() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(-0.5f);
        when(this.userAccCreateMapper.mapToEntity((CreateUserAccountDto) any())).thenReturn(account);

        Account account1 = new Account();
        account1.setNickName("Nick Name");
        account1.setUsers(new ArrayList<User>());
        account1.setId(1);
        account1.setInterestRate(10.0);
        account1.setActiveStatus(true);
        account1.setOpenDate(LocalDate.ofEpochDay(1L));
        account1.setAccountType(AccountType.CHECKING);
        account1.setBalance(10.0f);
        when(this.accountRepository.save((Account) any())).thenReturn(account1);
        this.accountService
                .createAccount(new CreateUserAccountDto(AccountType.CHECKING, 10.0f, new ArrayList<Integer>(), "Nick Name"));
        verify(this.userAccCreateMapper).mapToEntity((CreateUserAccountDto) any());
        verify(this.accountRepository).save((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testCreateAccount4() {
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.userRepository.findById((Integer) any())).thenReturn(ofResult);

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        when(this.userAccCreateMapper.mapToEntity((CreateUserAccountDto) any())).thenReturn(account);

        Account account1 = new Account();
        account1.setNickName("Nick Name");
        account1.setUsers(new ArrayList<User>());
        account1.setId(1);
        account1.setInterestRate(10.0);
        account1.setActiveStatus(true);
        account1.setOpenDate(LocalDate.ofEpochDay(1L));
        account1.setAccountType(AccountType.CHECKING);
        account1.setBalance(10.0f);
        when(this.accountRepository.save((Account) any())).thenReturn(account1);

        ArrayList<Integer> integerList = new ArrayList<Integer>();
        integerList.add(2);
        this.accountService.createAccount(new CreateUserAccountDto(AccountType.CHECKING, 10.0f, integerList, "Nick Name"));
        verify(this.userRepository).findById((Integer) any());
        verify(this.userAccCreateMapper).mapToEntity((CreateUserAccountDto) any());
        verify(this.accountRepository).save((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testCreateAccount5() {
        when(this.userRepository.findById((Integer) any())).thenReturn(Optional.<User>empty());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        when(this.userAccCreateMapper.mapToEntity((CreateUserAccountDto) any())).thenReturn(account);

        Account account1 = new Account();
        account1.setNickName("Nick Name");
        account1.setUsers(new ArrayList<User>());
        account1.setId(1);
        account1.setInterestRate(10.0);
        account1.setActiveStatus(true);
        account1.setOpenDate(LocalDate.ofEpochDay(1L));
        account1.setAccountType(AccountType.CHECKING);
        account1.setBalance(10.0f);
        when(this.accountRepository.save((Account) any())).thenReturn(account1);

        ArrayList<Integer> integerList = new ArrayList<Integer>();
        integerList.add(2);
        this.accountService.createAccount(new CreateUserAccountDto(AccountType.CHECKING, 10.0f, integerList, "Nick Name"));
        verify(this.userRepository).findById((Integer) any());
        verify(this.userAccCreateMapper).mapToEntity((CreateUserAccountDto) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }
}


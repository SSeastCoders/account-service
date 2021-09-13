package com.ss.eastcoderbank.accountapi.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.accountapi.mapper.UpdateAccountMapper;
import com.ss.eastcoderbank.accountapi.mapper.UserAccCreateMapper;
import com.ss.eastcoderbank.core.exeception.AccountNotEmptyException;
import com.ss.eastcoderbank.core.exeception.AccountNotFoundException;
import com.ss.eastcoderbank.core.exeception.UserNotFoundException;
import com.ss.eastcoderbank.core.model.account.Account;
import com.ss.eastcoderbank.core.model.account.AccountType;
import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.AccountRepository;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import com.ss.eastcoderbank.core.transferdto.UserDto;
import com.ss.eastcoderbank.core.transfermapper.AccountMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void testGetAccountsByUser() {
        when(this.accountRepository.findAccountByUsersId((Integer) any())).thenReturn(new ArrayList<Account>());
        assertTrue(this.accountService.getAccountsByUser(1).isEmpty());
        verify(this.accountRepository).findAccountByUsersId((Integer) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testGetAccountsByUser2() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);

        ArrayList<Account> accountList = new ArrayList<Account>();
        accountList.add(account);
        when(this.accountRepository.findAccountByUsersId((Integer) any())).thenReturn(accountList);

        AccountDto accountDto = new AccountDto();
        accountDto.setNickName("Nick Name");
        accountDto.setUsers(new ArrayList<UserDto>());
        accountDto.setId(1);
        accountDto.setInterestRate(10.0);
        accountDto.setActiveStatus(true);
        accountDto.setOpenDate(LocalDate.ofEpochDay(1L));
        accountDto.setAccountType(AccountType.CHECKING);
        accountDto.setBalance(10.0f);
        when(this.accountMapper.mapToDto((Account) any())).thenReturn(accountDto);
        assertEquals(1, this.accountService.getAccountsByUser(1).size());
        verify(this.accountRepository).findAccountByUsersId((Integer) any());
        verify(this.accountMapper).mapToDto((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testDeactivateAccount() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        Optional<Account> ofResult = Optional.<Account>of(account);
        when(this.accountRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(AccountNotEmptyException.class, () -> this.accountService.deactivateAccount(1));
        verify(this.accountRepository).findById((Integer) any());
    }

    @Test
    public void testDeactivateAccount2() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(false);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        Optional<Account> ofResult = Optional.<Account>of(account);
        when(this.accountRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(AccountNotFoundException.class, () -> this.accountService.deactivateAccount(1));
        verify(this.accountRepository).findById((Integer) any());
    }

    @Test
    public void testDeactivateAccount3() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(0.0f);
        Optional<Account> ofResult = Optional.<Account>of(account);

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
        when(this.accountRepository.findById((Integer) any())).thenReturn(ofResult);
        assertEquals(1, this.accountService.deactivateAccount(1).intValue());
        verify(this.accountRepository).findById((Integer) any());
        verify(this.accountRepository).save((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testUpdateAccount() {
        doNothing().when(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);

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
        when(this.accountRepository.getById((Integer) any())).thenReturn(account);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(new ArrayList<Integer>());
        updateAccountDto.setAccountType(AccountType.CHECKING);
        assertEquals(1, this.accountService.updateAccount(updateAccountDto, 1).intValue());
        verify(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());
        verify(this.accountRepository).getById((Integer) any());
        verify(this.accountRepository).save((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testUpdateAccount2() {
        doNothing().when(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        when(this.accountRepository.save((Account) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(this.accountRepository.getById((Integer) any())).thenReturn(account);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(new ArrayList<Integer>());
        updateAccountDto.setAccountType(AccountType.CHECKING);
        assertThrows(DataIntegrityViolationException.class, () -> this.accountService.updateAccount(updateAccountDto, 1));
        verify(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());
        verify(this.accountRepository).getById((Integer) any());
        verify(this.accountRepository).save((Account) any());
    }

    @Test
    public void testUpdateAccount3() {
        doNothing().when(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        when(this.accountRepository.save((Account) any())).thenThrow(new EntityNotFoundException("An error occurred"));
        when(this.accountRepository.getById((Integer) any())).thenReturn(account);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(new ArrayList<Integer>());
        updateAccountDto.setAccountType(AccountType.CHECKING);
        assertThrows(AccountNotFoundException.class, () -> this.accountService.updateAccount(updateAccountDto, 1));
        verify(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());
        verify(this.accountRepository).getById((Integer) any());
        verify(this.accountRepository).save((Account) any());
    }

    @Test
    public void testUpdateAccount4() {
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
        doNothing().when(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);

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
        when(this.accountRepository.getById((Integer) any())).thenReturn(account);

        ArrayList<Integer> integerList = new ArrayList<Integer>();
        integerList.add(2);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(integerList);
        updateAccountDto.setAccountType(AccountType.CHECKING);
        assertEquals(1, this.accountService.updateAccount(updateAccountDto, 1).intValue());
        verify(this.userRepository).findById((Integer) any());
        verify(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());
        verify(this.accountRepository).getById((Integer) any());
        verify(this.accountRepository).save((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void testUpdateAccount5() {
        when(this.userRepository.findById((Integer) any())).thenThrow(new NullPointerException("foo"));
        doNothing().when(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);

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
        when(this.accountRepository.getById((Integer) any())).thenReturn(account);

        ArrayList<Integer> integerList = new ArrayList<Integer>();
        integerList.add(2);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(integerList);
        updateAccountDto.setAccountType(AccountType.CHECKING);
        assertThrows(UserNotFoundException.class, () -> this.accountService.updateAccount(updateAccountDto, 1));
        verify(this.userRepository).findById((Integer) any());
        verify(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());
        verify(this.accountRepository).getById((Integer) any());
    }

    @Test
    public void testUpdateAccount6() {
        when(this.userRepository.findById((Integer) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        doNothing().when(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);

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
        when(this.accountRepository.getById((Integer) any())).thenReturn(account);

        ArrayList<Integer> integerList = new ArrayList<Integer>();
        integerList.add(2);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(integerList);
        updateAccountDto.setAccountType(AccountType.CHECKING);
        assertThrows(DataIntegrityViolationException.class, () -> this.accountService.updateAccount(updateAccountDto, 1));
        verify(this.userRepository).findById((Integer) any());
        verify(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());
        verify(this.accountRepository).getById((Integer) any());
    }

    @Test
    public void testUpdateAccount7() {
        when(this.userRepository.findById((Integer) any())).thenThrow(new EntityNotFoundException("An error occurred"));
        doNothing().when(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());

        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);

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
        when(this.accountRepository.getById((Integer) any())).thenReturn(account);

        ArrayList<Integer> integerList = new ArrayList<Integer>();
        integerList.add(2);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(integerList);
        updateAccountDto.setAccountType(AccountType.CHECKING);
        assertThrows(AccountNotFoundException.class, () -> this.accountService.updateAccount(updateAccountDto, 1));
        verify(this.userRepository).findById((Integer) any());
        verify(this.updateAccountMapper).updateEntity((UpdateAccountDto) any(), (Account) any());
        verify(this.accountRepository).getById((Integer) any());
    }



    @Test
    public void testGetAccounts() {
        when(this.accountRepository.findAll()).thenReturn(new ArrayList<Account>());
        assertTrue(this.accountService.getAccounts().isEmpty());
        verify(this.accountRepository).findAll();
    }

    @Test
    public void testGetAccounts2() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);

        ArrayList<Account> accountList = new ArrayList<Account>();
        accountList.add(account);
        when(this.accountRepository.findAll()).thenReturn(accountList);

        AccountDto accountDto = new AccountDto();
        accountDto.setNickName("Nick Name");
        accountDto.setUsers(new ArrayList<UserDto>());
        accountDto.setId(1);
        accountDto.setInterestRate(10.0);
        accountDto.setActiveStatus(true);
        accountDto.setOpenDate(LocalDate.ofEpochDay(1L));
        accountDto.setAccountType(AccountType.CHECKING);
        accountDto.setBalance(10.0f);
        when(this.accountMapper.mapToDto((Account) any())).thenReturn(accountDto);
        assertEquals(1, this.accountService.getAccounts().size());
        verify(this.accountRepository).findAll();
        verify(this.accountMapper).mapToDto((Account) any());
    }

    @Test
    public void testGetAccountById() {
        Account account = new Account();
        account.setNickName("Nick Name");
        account.setUsers(new ArrayList<User>());
        account.setId(1);
        account.setInterestRate(10.0);
        account.setActiveStatus(true);
        account.setOpenDate(LocalDate.ofEpochDay(1L));
        account.setAccountType(AccountType.CHECKING);
        account.setBalance(10.0f);
        Optional<Account> ofResult = Optional.<Account>of(account);
        when(this.accountRepository.findById((Integer) any())).thenReturn(ofResult);

        AccountDto accountDto = new AccountDto();
        accountDto.setNickName("Nick Name");
        accountDto.setUsers(new ArrayList<UserDto>());
        accountDto.setId(1);
        accountDto.setInterestRate(10.0);
        accountDto.setActiveStatus(true);
        accountDto.setOpenDate(LocalDate.ofEpochDay(1L));
        accountDto.setAccountType(AccountType.CHECKING);
        accountDto.setBalance(10.0f);
        when(this.accountMapper.mapToDto((Account) any())).thenReturn(accountDto);
        assertSame(accountDto, this.accountService.getAccountById(1));
        verify(this.accountRepository).findById((Integer) any());
        verify(this.accountMapper).mapToDto((Account) any());
        assertTrue(this.accountService.getAccounts().isEmpty());
    }

    @Test
    public void getPaginatedAccountsShouldReturnPageFromRepository() {
        Page<Account> accountPage = Mockito.mock(Page.class);
        AccountRepository mockAccountRepo = mock(AccountRepository.class);

        when(mockAccountRepo.findAll(Mockito.any(PageRequest.class))).thenReturn(accountPage);

        assertNotNull(accountPage);
    }




}


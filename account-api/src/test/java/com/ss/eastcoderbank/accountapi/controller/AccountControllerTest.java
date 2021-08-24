package com.ss.eastcoderbank.accountapi.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.accountapi.service.AccountService;
import com.ss.eastcoderbank.core.model.account.AccountType;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import com.ss.eastcoderbank.core.transferdto.UserDto;

import java.time.LocalDate;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
public class AccountControllerTest {
    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    @Test
    public void testDeactivateAccount() throws Exception {
        when(this.accountService.deactivateAccount((Integer) any())).thenReturn(3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/accounts/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Account deleted"));
    }

    @Test
    public void testDeactivateAccount2() throws Exception {
        when(this.accountService.deactivateAccount((Integer) any())).thenReturn(3);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/accounts/{id}", 1);
        deleteResult.contentType("Not all who wander are lost");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Account deleted"));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        when(this.accountService.updateAccount((UpdateAccountDto) any(), (Integer) any())).thenReturn(3);

        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setNickName("Nick Name");
        updateAccountDto.setUsersIds(new ArrayList<Integer>());
        updateAccountDto.setAccountType(AccountType.CHECKING);
        String content = (new ObjectMapper()).writeValueAsString(updateAccountDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/accounts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(206));
    }

    @Test
    public void testCreateAccount() throws Exception {
        when(this.accountService.getAccounts()).thenReturn(new ArrayList<AccountDto>());

        CreateUserAccountDto createUserAccountDto = new CreateUserAccountDto();
        createUserAccountDto.setNickName("Nick Name");
        createUserAccountDto.setUsersIds(new ArrayList<Integer>());
        createUserAccountDto.setAccountType(AccountType.CHECKING);
        createUserAccountDto.setBalance(10.0f);
        String content = (new ObjectMapper()).writeValueAsString(createUserAccountDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAccount() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setNickName("Nick Name");
        accountDto.setUsers(new ArrayList<UserDto>());
        accountDto.setId(1);
        accountDto.setInterestRate(10.0);
        accountDto.setActiveStatus(true);
        accountDto.setOpenDate(LocalDate.ofEpochDay(1L));
        accountDto.setAccountType(AccountType.CHECKING);
        accountDto.setBalance(10.0f);
        when(this.accountService.getAccountById((Integer) any())).thenReturn(accountDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"accountType\":\"CHECKING\",\"users\":[],\"interestRate\":10.0,\"openDate\":[1970,1,2],\"balance\":10.0"
                                        + ",\"activeStatus\":true,\"nickName\":\"Nick Name\"}"));
    }

    @Test
    public void testGetAccountByUser() throws Exception {
        when(this.accountService.getAccountsByUser((Integer) any())).thenReturn(new ArrayList<AccountDto>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts/users/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAccountByUser2() throws Exception {
        when(this.accountService.getAccountsByUser((Integer) any())).thenReturn(new ArrayList<AccountDto>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/accounts/users/{id}", 1);
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        when(this.accountService.getAccounts()).thenReturn(new ArrayList<AccountDto>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllAccounts2() throws Exception {
        when(this.accountService.getAccounts()).thenReturn(new ArrayList<AccountDto>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllAccounts3() throws Exception {
        when(this.accountService.getAccounts()).thenReturn(new ArrayList<AccountDto>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllAccounts4() throws Exception {
        when(this.accountService.getAccounts()).thenReturn(new ArrayList<AccountDto>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/accounts");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}


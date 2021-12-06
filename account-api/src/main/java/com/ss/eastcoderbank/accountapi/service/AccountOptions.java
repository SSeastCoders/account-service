package com.ss.eastcoderbank.accountapi.service;

import com.ss.eastcoderbank.core.model.account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class AccountOptions {
    private final String nickName;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final Float fromAmount;
    private final Float toAmount;
    private final AccountType accountType;
    private final Boolean activeStatus;

}

package com.ss.eastcoderbank.accountapi.dto;

import com.ss.eastcoderbank.core.model.account.AccountType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserAccountDto {
    @NotNull
    protected AccountType accountType;

    @NotNull
    protected List<Integer> usersIds;
}

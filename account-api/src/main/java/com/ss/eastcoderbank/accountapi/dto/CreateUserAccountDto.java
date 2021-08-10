package com.ss.eastcoderbank.accountapi.dto;

import com.ss.eastcoderbank.core.model.account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserAccountDto {
    @NotNull
    protected AccountType accountType;

    @NotNull
    protected Float balance;

    @NotNull
    protected List<Integer> usersIds;
}

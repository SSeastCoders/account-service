package com.ss.eastcoderbank.accountapi.dto;

import com.ss.eastcoderbank.core.model.account.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
public class UpdateAccountDto {
    private AccountType accountType;

    private List<Integer> usersIds;

    @Size(max = 20, message = "name must be less then 20 characters")
    private String nickName;
}

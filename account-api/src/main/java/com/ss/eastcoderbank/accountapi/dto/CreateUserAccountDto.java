package com.ss.eastcoderbank.accountapi.dto;

import com.ss.eastcoderbank.core.model.account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserAccountDto {
    @NotNull
    private AccountType accountType;

    @NotNull
    @Min(value = 0, message = "Balance can not be negative.")
    private Float balance;

    @NotNull
    private List<Integer> usersIds;

    @NotBlank
    @Size(max = 20, message = "name must be less then 20 characters")
    private String nickName;
}

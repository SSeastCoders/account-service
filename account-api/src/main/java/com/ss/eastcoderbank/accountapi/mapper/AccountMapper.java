package com.ss.eastcoderbank.accountapi.mapper;

import com.ss.eastcoderbank.core.model.account.Account;
import com.ss.eastcoderbank.core.transferdto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto mapToDto(Account account);
}

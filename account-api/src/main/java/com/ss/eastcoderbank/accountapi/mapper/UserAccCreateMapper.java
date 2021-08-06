package com.ss.eastcoderbank.accountapi.mapper;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.core.model.account.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccCreateMapper {

    Account mapToEntity(CreateUserAccountDto accountCreateDto);
}

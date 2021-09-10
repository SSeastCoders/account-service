package com.ss.eastcoderbank.accountapi.mapper;

import com.ss.eastcoderbank.accountapi.dto.CreateUserAccountDto;
import com.ss.eastcoderbank.core.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccCreateMapper {

    @Mapping(source = "balance", target = "balance")
    Account mapToEntity(CreateUserAccountDto accountCreateDto);
}

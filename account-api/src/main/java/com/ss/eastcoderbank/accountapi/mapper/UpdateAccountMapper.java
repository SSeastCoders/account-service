package com.ss.eastcoderbank.accountapi.mapper;

import com.ss.eastcoderbank.accountapi.dto.UpdateAccountDto;
import com.ss.eastcoderbank.core.model.account.Account;
import com.ss.eastcoderbank.core.model.account.AccountType;
import org.mapstruct.*;

import javax.validation.constraints.Size;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateAccountMapper {

    @Mapping(ignore = true, target = "balance")
    @Mapping(ignore = true, target = "users")
    void updateEntity(UpdateAccountDto updateAccountDto, @MappingTarget Account account);

    @AfterMapping
    default void updateEntity(@MappingTarget Account account, UpdateAccountDto updateAccountDto) {
    }
}

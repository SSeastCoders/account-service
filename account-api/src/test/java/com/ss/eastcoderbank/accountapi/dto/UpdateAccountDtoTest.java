package com.ss.eastcoderbank.accountapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.ss.eastcoderbank.core.model.account.AccountType;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UpdateAccountDtoTest {
    @Test
    public void testConstructor() {
        UpdateAccountDto actualUpdateAccountDto = new UpdateAccountDto();
        actualUpdateAccountDto.setAccountType(AccountType.CHECKING);
        actualUpdateAccountDto.setNickName("Nick Name");
        ArrayList<Integer> integerList = new ArrayList<Integer>();
        actualUpdateAccountDto.setUsersIds(integerList);
        assertEquals(AccountType.CHECKING, actualUpdateAccountDto.getAccountType());
        assertEquals("Nick Name", actualUpdateAccountDto.getNickName());
        assertSame(integerList, actualUpdateAccountDto.getUsersIds());
    }
}


package com.example.SOMusic.service;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

    public static final String EXISTING_ID = "amy1234";
    @InjectMocks
    AccountService accountService = new AccountServiceImpl();

    @Mock
    AccountRepository accountRepository;

    Account account = createTestAccount();
    Account duplicatedAccount = createTestAccount();

    @Test
    void 서비스를_통한_Account_중복_테스트(){
        String newId = account.getUserId();
        String existingId = duplicatedAccount.getUserId();

        boolean isDuplicated = isTwoIdSame(newId, existingId);

        assertThat(isDuplicated).isTrue();
    }

    @Test
    void 서비스를_통한_Account_중복_없음_테스트(){
        String newId = account.getUserId();
        String existingId = EXISTING_ID;

        boolean isDuplicated = isTwoIdSame(newId, existingId);

        assertThat(isDuplicated).isFalse();
    }

    public boolean isTwoIdSame(String newId, String existingId){
        Mockito.when(accountRepository
                        .existsById(newId))
                .thenReturn(newId.equalsIgnoreCase(existingId));

        return accountService.isDuplicated(existingId);
    }

    public Account createTestAccount(){
        Account account = new Account();

        account.setUserId("mark123");
        account.setUserName("mark");
        account.setEmail("mark123@gmail.com");
        account.setPhone("01012345678");
        account.setAddress("earth");
        account.setZipcode("12345");

        return account;
    }
}

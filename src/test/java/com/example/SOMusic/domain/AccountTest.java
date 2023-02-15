package com.example.SOMusic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SOMusic.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountTest {
    Account account = createTestAccount();

    @Test
    void Account_생성_성공_테스트(){
        assertThat(account.getUserId()).isEqualTo("mark123");
        assertThat(account.getUserName()).isEqualTo("mark");
    }

    @Test
    void Account_수정_성공_테스트(){
        account.setUserName("somsom");

        assertThat(account.getUserId()).isEqualTo("mark123");
        assertThat(account.getUserName()).isEqualTo("somsom");
    }

    public Account createTestAccount(){
        Account account = new Account(); //TODO: 비어있는 객체 생성을 막지 않음

        account.setUserId("mark123");
        account.setUserName("mark");
        account.setEmail("mark123@gmail.com");
        account.setPhone("01012345678");
        account.setAddress("earth");
        account.setZipcode("12345");

        return account;
    }
}

package com.example.SOMusic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SOMusic.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class AccountRepositoryTest {

    private static final int LIST_SIZE = 2;
    @Autowired
    AccountRepository accountRepository;
    Account account1 = createTestAccount();
    Account account2 = createAnotherTestAccount();

    @Test
    void Account_저장_성공_테스트(){
        Account result = accountRepository.save(account1);

        assertThat(result.getUserId()).isEqualTo(account1.getUserId());
    }

    @Test
    void Account_리스트_불러오기_테스트(){
        accountRepository.save(account1);
        accountRepository.save(account2);

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts.size()).isEqualTo(LIST_SIZE);
    }

    @Test
    void 전화번호_이메일_계정찾기_테스트(){
        Account account = accountRepository.save(account2);

        Account searchResult = accountRepository.findAccountByUserIdAndPhone(
                "amy1234", "01011112222");

        assertThat(searchResult.getUserName()).isEqualTo(account.getUserName());
    }

    @Test
    void 아이디_전화번호_계정찾기_테스트(){
        Account account = accountRepository.save(account2);

        Account searchResult = accountRepository.findAccountByPhoneAndEmail(
                "01011112222", "amy1234@gmail.com");

        assertThat(searchResult.getUserName()).isEqualTo(account.getUserName());
    }

    public Account createTestAccount(){
        Account account = new Account();

        account.setUserId("mark123");
        account.setUserName("mark");
        account.setEmail("mark123@gmail.com");
        account.setPhone("01012345678");

        return account;
    }

    public Account createAnotherTestAccount(){
        Account account = new Account();

        account.setUserId("amy1234");
        account.setUserName("amy");
        account.setEmail("amy1234@gmail.com");
        account.setPhone("01011112222");

        return account;
    }
}

package com.example.SOMusic.repository;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.TestAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {

    private static final int LIST_SIZE = 2;
    @Autowired
    AccountRepository accountRepository;
    Account account1 = TestAccount.createTestAccount();
    Account account2 = TestAccount.createAnotherTestAccount();

    @Test
    @DisplayName("Account_저장_성공_테스트")
    void save() {
        Account result = accountRepository.save(account1);

        assertThat(result.getUserId()).isEqualTo(account1.getUserId());
    }

    @Test
    @DisplayName("Account_리스트_불러오기_테스트")
    void findAll() {
        accountRepository.save(account1);
        accountRepository.save(account2);

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts.size()).isEqualTo(LIST_SIZE);
    }

    @Test
    @DisplayName("전화번호_이메일_계정찾기_테스트")
    void findAccountByUserIdAndPhone() {
        Account account = accountRepository.save(account2);

        Account searchResult = accountRepository.findAccountByUserIdAndPhone(
                "amy1234", "01011112222");

        assertThat(searchResult.getUserName()).isEqualTo(account.getUserName());
    }

    @Test
    @DisplayName("아이디_전화번호_계정찾기_테스트")
    void findAccountByPhoneAndEmail() {
        Account account = accountRepository.save(account2);

        Account searchResult = accountRepository.findAccountByPhoneAndEmail(
                "01011112222", "amy1234@gmail.com");

        assertThat(searchResult.getUserName()).isEqualTo(account.getUserName());
    }
}

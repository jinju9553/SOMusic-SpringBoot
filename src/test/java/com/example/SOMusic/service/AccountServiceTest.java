package com.example.SOMusic.service;

import com.example.SOMusic.dao.AccountDao;
import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.TestAccount;
import com.example.SOMusic.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

    public static final String EXISTING_ID = "amy1234";
    private static final String NEW_PHONE = "01057939101";
    private static final String NEW_ADDRESS = "planet";

    private static final String NEW_PASSWORD = "newabcd";
    private final ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
    @InjectMocks
    AccountService accountService = new AccountServiceImpl();

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountDao accountDao;

    Account account = TestAccount.createTestAccount();
    Account duplicatedAccount = TestAccount.createTestAccount();

    @Test
    void getAccount() {
        String userId = account.getUserId();

        Mockito.when(accountDao.getAccount(userId)).thenReturn(account);

        Account result = accountService.getAccount(userId);
        String resultId = result.getUserId();

        assertThat(resultId).isEqualTo(userId);
    }

    @Test
    void findAccount() {
        String userId = account.getUserId();
        String phone = account.getPhone();
        String email = account.getEmail();

        Mockito.when(accountRepository.findAccountByPhoneAndEmail(phone, email))
                .thenReturn(account);

        Account result = accountService.findAccount(phone, email);
        String resultId = result.getUserId();

        assertThat(resultId).isEqualTo(userId);
    }

    @Test
    void findPassword() {
        String userId = account.getUserId();
        String phone = account.getPhone();

        Mockito.when(accountRepository.findAccountByUserIdAndPhone(userId, phone))
                .thenReturn(account);

        Account result = accountService.findPassword(userId, phone);
        String resultId = result.getUserId();

        assertThat(resultId).isEqualTo(userId);
    }

    @Test
    void insertAccount() {
        String userId = account.getUserId();

        accountDao.insertAccount(account);

        Mockito.verify(accountDao).insertAccount(captor.capture());
        String result = captor.getValue().getUserId();

        assertThat(result).isEqualTo(userId);
    }

    @Test
    void updateAccount() {
        String userId = account.getUserId();

        Account accountReq = getTestAccountReq(account);
        accountDao.updateAccount(accountReq);

        Mockito.verify(accountDao).updateAccount(captor.capture());
        String result = captor.getValue().getUserId();

        assertThat(result).isEqualTo(userId);
    }

    private Account getTestAccountReq(Account account) {
        Account accountReq = new Account();

        accountReq.setUserId(account.getUserId());
        accountReq.setPhone(NEW_PHONE);
        accountReq.setAddress(NEW_ADDRESS);

        return accountReq;
    }

    @Test
    void deleteAccount() {
        String userId = account.getUserId();

        accountDao.removeAccount(account);

        Mockito.verify(accountDao).removeAccount(captor.capture());
        String result = captor.getValue().getUserId();

        assertThat(result).isEqualTo(userId);
    }

    @Test
    void updatePassword() {
        String userId = account.getUserId();

        accountDao.updatePassword(account, NEW_PASSWORD);

        Mockito.verify(accountDao).updatePassword(captor.capture(), eq(NEW_PASSWORD));
        String result = captor.getValue().getUserId();

        assertThat(result).isEqualTo(userId);
    }

    @Test
    @DisplayName("isDuplicated_중복_있음_테스트")
    void isDuplicated() {
        String newId = account.getUserId();
        String existingId = duplicatedAccount.getUserId();

        boolean isDuplicated = isTwoIdSame(newId, existingId);

        assertThat(isDuplicated).isTrue();
    }

    @Test
    @DisplayName("isDuplicated_중복_없음_테스트")
    void isNotDuplicated() {
        String newId = account.getUserId();
        String existingId = EXISTING_ID;

        boolean isDuplicated = isTwoIdSame(newId, existingId);

        assertThat(isDuplicated).isFalse();
    }

    public boolean isTwoIdSame(String newId, String existingId) {
        Mockito.when(accountRepository
                        .existsById(newId))
                .thenReturn(newId.equalsIgnoreCase(existingId));

        return accountService.isDuplicated(existingId);
    }
}

package com.example.SOMusic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

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

    @Test
    void Account_zipcode_테스트(){
        account.setZipcode("12345");

        assertThat(account.matchesZipcode()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidZipcode")
    void Account_zipcode_실패_테스트(String zipcode){
        account.setZipcode(zipcode);

        assertThat(account.matchesZipcode()).isFalse();
    }

    @Test
    void Account_phone_테스트(){
        account.setPhone("01012345678");

        assertThat(account.matchesPhone()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPhone")
    void Account_phone_실패_테스트(String phone){
        account.setPhone(phone);

        assertThat(account.matchesPhone()).isFalse();
    }

    public Account createTestAccount(){
        Account account = new Account();

        account.setUserId("mark123");
        account.setUserName("mark");
        account.setEmail("mark123@gmail.com");
        account.setAddress("earth");

        return account;
    }

    private static Stream<Arguments> provideInvalidZipcode(){
        return Stream.of(
                Arguments.of("1235"),
                Arguments.of("4638192"),
                Arguments.of("123cd"),
                Arguments.of("abcde")
        );
    }

    private static Stream<Arguments> provideInvalidPhone(){
        return Stream.of(
                Arguments.of("12312345678"),
                Arguments.of("0103456"),
                Arguments.of("010abc3456"),
                Arguments.of("01abcd3456"),
                Arguments.of("abcdef")
        );
    }
}

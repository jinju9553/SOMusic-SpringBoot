package com.example.SOMusic.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JoinTest {

    Join join = createTestJoin();

    @Test
    void Purchase_생성_성공_테스트(){
        assertThat(join.getConsumerId()).isEqualTo("amy1234");
        assertThat(join.getConsumerName()).isEqualTo("amy");
    }

    @Test
    void Purchase_수정_성공_테스트(){
        join.setConsumerName("harry");

        assertThat(join.getConsumerId()).isEqualTo("amy1234");
        assertThat(join.getConsumerName()).isEqualTo("harry");
    }

    @Test
    void Join_zipcode_테스트() {
        join.setZipcode("23456");

        assertThat(join.matchesZipcode()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidZipcode")
    void Join_zipcode_실패_테스트(String zipcode){
        join.setZipcode(zipcode);

        assertThat(join.matchesZipcode()).isFalse();
    }

    @Test
    void Join_phone_테스트() {
        join.setPhone("01012345678");

        assertThat(join.matchesPhone()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPhone")
    void Join_phone_실패_테스트(String phone){
        join.setPhone(phone);

        assertThat(join.matchesPhone()).isFalse();
    }

    public Join createTestJoin(){
        Join join = new Join();

        join.setConsumerId("amy1234");
        join.setConsumerName("amy");
        join.setConsumerBank("BankName");
        join.setTotalAmount(3);

        return join;
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
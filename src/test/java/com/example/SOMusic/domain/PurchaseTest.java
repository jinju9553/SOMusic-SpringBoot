package com.example.SOMusic.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PurchaseTest {

    Purchase purchase = createTestPurchase();

    @Test
    void Purchase_생성_성공_테스트(){
        assertThat(purchase.getConsumerId()).isEqualTo("mark123");
        assertThat(purchase.getConsumerName()).isEqualTo("mark");
    }

    @Test
    void Purchase_수정_성공_테스트(){
        purchase.setConsumerName("harry");

        assertThat(purchase.getConsumerId()).isEqualTo("mark123");
        assertThat(purchase.getConsumerName()).isEqualTo("harry");
    }

    @Test
    void Purchase_zipcode_테스트(){
        purchase.setZipcode("12346");

        assertThat(purchase.matchesZipcode()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidZipcode")
    void Purchase_zipcode_실패_테스트(String zipcode){
        purchase.setZipcode(zipcode);

        assertThat(purchase.matchesZipcode()).isFalse();
    }

    @Test
    void Purchase_phone_테스트(){
        purchase.setPhone("01012345678");

        assertThat(purchase.matchesPhone()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPhone")
    void Purchase_phone_실패_테스트(String phone){
        purchase.setPhone(phone);

        assertThat(purchase.matchesPhone()).isFalse();
    }

    public Purchase createTestPurchase(){
        Purchase purchase = new Purchase();

        purchase.setConsumerId("mark123");
        purchase.setConsumerName("mark");
        purchase.setTotalAmount(3);
        purchase.setStatus(1);
        purchase.setShippingMethod(0);

        return purchase;
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

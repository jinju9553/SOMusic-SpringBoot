package com.example.SOMusic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.domain.TestPurchase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class PurchaseRepositoryTest {
    private static final String CONSUMER_ID = "mark123";
    private static final int LIST_SIZE = 2;

    @Autowired
    PurchaseRepository purchaseRepository;

    Purchase purchase1 = TestPurchase.createTestPurchase();
    Purchase purchase2 = TestPurchase.createAnotherTestPurchase();

    @Test
    @DisplayName("구매아이디로_구매정보_찾기_테스트")
    void findPurchaseByPurchaseId() {
        Purchase saveResult = purchaseRepository.save(purchase1);

        Purchase searchResult = purchaseRepository.findPurchaseByPurchaseId(
                saveResult.getPurchaseId());

        String searchedName = searchResult.getConsumerName();
        String savedName = saveResult.getConsumerName();

        assertThat(searchedName).isEqualTo(savedName);
    }

    @Test
    @DisplayName("사용자아이디로_구매정보_찾기_테스트")
    void findPurchaseByConsumerId() {
        Purchase saveResult = purchaseRepository.save(purchase1);

        Purchase searchResult = purchaseRepository.findPurchaseByConsumerId(
                saveResult.getConsumerId());

        String searchedName = searchResult.getConsumerName();
        String savedName = saveResult.getConsumerName();

        assertThat(searchedName).isEqualTo(savedName);
    }

    @Test
    @DisplayName("사용자아이디로_모든_구매정보_찾기_테스트")
    void findAllByConsumerId() {
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        List<Purchase> purchases = purchaseRepository.findAllByConsumerId(CONSUMER_ID);

        assertThat(purchases.size()).isEqualTo(LIST_SIZE);
    }
}
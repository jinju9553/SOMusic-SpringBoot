package com.example.SOMusic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
class PurchaseRepositoryTest {

    private static final int PURCHASE_ID = 5723;
    private static final String CONSUMER_ID = "mark123";
    private static final int LIST_SIZE = 2;

    @Autowired
    PurchaseRepository purchaseRepository;

    Purchase purchase1 = createTestPurchase();
    Purchase purchase2 = createAnotherTestPurchase();

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
        Purchase saveResult1 = purchaseRepository.save(purchase1);
        Purchase saveResult2 = purchaseRepository.save(purchase2);

        List<Purchase> purchases = purchaseRepository.findAllByConsumerId(CONSUMER_ID);

        assertThat(purchases.size()).isEqualTo(LIST_SIZE);
    }

    public Purchase createTestPurchase(){
        Purchase purchase = new Purchase();

        purchase.setPurchaseId(PURCHASE_ID);
        purchase.setConsumerId(CONSUMER_ID);
        purchase.setConsumerName("mark");
        purchase.setTotalAmount(3);
        purchase.setStatus(1);
        purchase.setShippingMethod(0);

        purchase.setRegDate(new Date());
        purchase.setProduct(new Product());

        return purchase;
    }

    public Purchase createAnotherTestPurchase(){
        Purchase purchase = new Purchase();

        purchase.setPurchaseId(PURCHASE_ID);
        purchase.setConsumerId(CONSUMER_ID);
        purchase.setConsumerName("mark");
        purchase.setTotalAmount(8);
        purchase.setStatus(2);
        purchase.setShippingMethod(2);

        purchase.setRegDate(new Date());
        purchase.setProduct(new Product());

        return purchase;
    }
}
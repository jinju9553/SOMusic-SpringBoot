package com.example.SOMusic.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SOMusic.dao.PurchaseDao;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.domain.TestPurchase;
import com.example.SOMusic.repository.PurchaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class PurchaseServiceTest {

    private static final int PURCHASE_ID = 5723;
    private static final String CONSUMER_ID = "mark123";

    private final ArgumentCaptor<Purchase> captor = ArgumentCaptor.forClass(Purchase.class);

    @InjectMocks
    PurchaseService purchaseService = new PurchaseServiceImpl();

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    PurchaseDao purchaseDao;

    Purchase purchase1 = TestPurchase.createTestPurchase();
    Purchase purchase2 = TestPurchase.createTestPurchase();

    @Test
    @DisplayName("Service_Dao를_통한_Purchase_등록")
    void registerPurchase() {
        int purchaseId = purchase1.getPurchaseId();

        purchaseDao.createPurchase(purchase1);

        Mockito.verify(purchaseDao).createPurchase(captor.capture());
        int result = captor.getValue().getPurchaseId();

        assertThat(result).isEqualTo(purchaseId);
    }

    @Test
    @DisplayName("Service_최종금액_계산_테스트")
    void calculateTotal() {
        Product product = purchase1.getProduct();
        int price = product.getPrice();
        int shippingCost = product.getShippingCost();

        int totalPrice = price * shippingCost;

        int result = purchaseService.calculateTotal(product);

        assertThat(result).isEqualTo(totalPrice);
    }

    @Test
    @DisplayName("Service_구매아이디로_구매정보_찾기_테스트")
    void findPurchaseByPurchaseId() {
        int purchaseId = purchase1.getPurchaseId();

        Mockito.when(purchaseRepository.findPurchaseByPurchaseId(purchaseId))
                .thenReturn(purchase1);

        Purchase result = purchaseRepository.findPurchaseByPurchaseId(purchaseId);

        assertThat(result.getPurchaseId()).isEqualTo(purchaseId);
    }

    @Test
    @DisplayName("Service_사용자아이디로_구매정보_찾기_테스트")
    void findPurchaseByUserId() {
        String userId = purchase1.getConsumerId();

        Mockito.when(purchaseRepository.findPurchaseByConsumerId(userId))
                .thenReturn(purchase1);

        Purchase result = purchaseService.findPurchaseByUserId(userId);

        assertThat(result.getConsumerId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("Service_사용자아이디로_모든_구매정보_찾기_테스트")
    void findPurchaseList() {
        String userId = purchase1.getConsumerId();

        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        List<Purchase> purchases = new LinkedList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        Mockito.when(purchaseRepository.findAllByConsumerId(userId))
                .thenReturn(purchases);

        List<Purchase> result = purchaseService.findPurchaseList(userId);

        assertThat(result.size()).isEqualTo(purchases.size());
    }

    @Test
    void modifyPurchaseInfo() {
        //TODO: 함수를 새로 설계하고 그 이후 테스트코드 다시 작성할 예정
    }

    @Test
    void confirmPurchase() {
        //TODO: 함수를 새로 설계하고 그 이후 테스트코드 다시 작성할 예정
    }
}
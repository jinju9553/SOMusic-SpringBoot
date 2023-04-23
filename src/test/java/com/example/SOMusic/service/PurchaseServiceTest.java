package com.example.SOMusic.service;

import com.example.SOMusic.dao.PurchaseDao;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.domain.TestPurchase;
import com.example.SOMusic.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class PurchaseServiceTest {

    private static final int CONFIRMED = 1;
    private static final String NEW_PHONE = "01057939101";
    private static final String NEW_ADDRESS = "planet";
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
    void registerPurchase() {
        int purchaseId = purchase1.getPurchaseId();

        purchaseDao.createPurchase(purchase1);

        Mockito.verify(purchaseDao).createPurchase(captor.capture());
        int result = captor.getValue().getPurchaseId();

        assertThat(result).isEqualTo(purchaseId);
    }

    @Test
    void calculateTotal() {
        Product product = purchase1.getProduct();
        int price = product.getPrice();
        int shippingCost = product.getShippingCost();

        int totalPrice = price * shippingCost;

        int result = purchaseService.calculateTotal(product);

        assertThat(result).isEqualTo(totalPrice);
    }

    @Test
    void findPurchaseByPurchaseId() {
        int purchaseId = purchase1.getPurchaseId();

        Mockito.when(purchaseRepository.findPurchaseByPurchaseId(purchaseId))
                .thenReturn(purchase1);

        Purchase result = purchaseRepository.findPurchaseByPurchaseId(purchaseId);

        assertThat(result.getPurchaseId()).isEqualTo(purchaseId);
    }

    @Test
    void findPurchaseByUserId() {
        String userId = purchase1.getConsumerId();

        Mockito.when(purchaseRepository.findPurchaseByConsumerId(userId))
                .thenReturn(purchase1);

        Purchase result = purchaseService.findPurchaseByUserId(userId);

        assertThat(result.getConsumerId()).isEqualTo(userId);
    }

    @Test
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
        String initialId = purchase1.getConsumerId();
        String initialPhone = purchase1.getPhone();
        String initialAddress = purchase1.getAddress();

        Purchase purchaseReq = getTestPurchaseReq(purchase1);

        purchaseService.modifyPurchaseInfo(purchase1, purchaseReq);

        assertThat(purchase1.getPhone()).isNotEqualTo(initialPhone);
        assertThat(purchase1.getAddress()).isNotEqualTo(initialAddress);
        assertThat(purchase1.getConsumerId()).isEqualTo(initialId);
    }

    private Purchase getTestPurchaseReq(Purchase purchase) {
        Purchase purchaseReq = new Purchase();

        purchaseReq.setConsumerName(purchase.getConsumerName());
        purchaseReq.setPhone(NEW_PHONE);
        purchaseReq.setAddress(NEW_ADDRESS);
        purchaseReq.setZipcode(purchase.getZipcode());
        purchaseReq.setShippingRequest(purchase.getShippingRequest());

        return purchaseReq;
    }

    @Test
    void confirmPurchase() {
        int initialStatus = purchase1.getStatus();

        purchaseService.confirmPurchase(purchase1);

        assertThat(purchase1.getStatus()).isEqualTo(CONFIRMED);
        assertThat(purchase1.getStatus()).isNotEqualTo(initialStatus);
    }
}
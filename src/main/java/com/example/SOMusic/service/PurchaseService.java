package com.example.SOMusic.service;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;

import java.util.List;

public interface PurchaseService {

    void registerPurchase(Purchase purchase);

    void modifyPurchaseInfo(Purchase purchase, Purchase purchaseReq);

    void deletePurchase(int purchaseId);

    int calculateTotal(Product product);

    Purchase findPurchaseByPurchaseId(int purchaseId);

    Purchase findPurchaseByUserId(String userId);

    List<Purchase> findPurchaseList(String userId);

    void confirmPurchase(Purchase purchase);
}

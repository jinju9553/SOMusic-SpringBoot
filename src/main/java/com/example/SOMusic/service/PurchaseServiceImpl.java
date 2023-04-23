package com.example.SOMusic.service;

import com.example.SOMusic.dao.PurchaseDao;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private static final int NOT_CONFIRMED = 0;
    private static final int CONFIRMED = 1;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    public void registerPurchase(Purchase purchase) {
        purchaseDao.createPurchase(purchase);
    }

    @Override
    public Purchase findPurchaseByUserId(String consumerId) {
        return purchaseRepository.findPurchaseByConsumerId(consumerId);
    }

    @Override
    public List<Purchase> findPurchaseList(String consumerId) {
        return purchaseRepository.findAllByConsumerId(consumerId);
    }

    @Override
    @Transactional
    public void modifyPurchaseInfo(Purchase purchase, Purchase purchaseReq) {
        purchase.setConsumerName(purchaseReq.getConsumerName());
        purchase.setPhone(purchaseReq.getPhone());
        purchase.setZipcode(purchaseReq.getZipcode());
        purchase.setAddress(purchaseReq.getAddress());
        purchase.setShippingRequest(purchaseReq.getShippingRequest());
    }

    @Override
    @Transactional
    public void deletePurchase(int purchaseId) {
        purchaseRepository.deleteByPurchaseId(purchaseId);
    }

    @Override
    public Purchase findPurchaseByPurchaseId(int purchaseId) {
        return purchaseRepository.findPurchaseByPurchaseId(purchaseId);
    }

    @Override
    public int calculateTotal(Product product) {
        return (product.getPrice() + product.getShippingCost());
    }

    @Override
    @Transactional
    public void confirmPurchase(Purchase purchase) {
        Product product = purchase.getProduct();
        if (product.getStatus() == NOT_CONFIRMED) {
            product.setStatus(CONFIRMED);
            purchase.setStatus(CONFIRMED);
        }
    }
}

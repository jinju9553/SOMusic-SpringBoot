package com.example.SOMusic.service;

import java.util.List;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;

public interface PurchaseService {

	void registerPurchase(Purchase purchase);
	
	void modifyPurchase(Purchase purchase);
	
	void deletePurchase(int purchaseId);
	
	int calculateTotal(Product product);
	
	/* Spring Data JPA */
	
	Purchase findPurchaseByPurchaseId(int purchaseId);
	
	Purchase findPurchaseByUserId(String userId);
	
	List<Purchase> findPurchaseList(String userId);

	void modifyPurchaseInfo(int purchaseId, Purchase purchase);

	void confirmPurchase(int purchaseId);
}

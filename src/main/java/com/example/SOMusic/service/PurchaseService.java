package com.example.SOMusic.service;

import java.util.List;

import com.example.SOMusic.domain.Purchase;

public interface PurchaseService {

	void registerPurchase(Purchase purchase);
	
	List<Purchase> findPurchaseList(String userId);
	
	void modifyPurchase(Purchase purchase);
	
	void deletePurchase(int purchaseId);
	
	/* Spring Data JPA */
	Purchase findPurchaseByPurchaseId(int purchaseId);
	
	Purchase findPurchaseByUserId(String userId);
}

package com.example.SOMusic.service;

import java.util.List;

import com.example.SOMusic.controller.PurchaseRequest;
import com.example.SOMusic.domain.Purchase;

public interface PurchaseService {

	Purchase registerPurchase(PurchaseRequest purchaseReq);
	
	Purchase findPurchase(String userId);
	
	List<Purchase> findPurchaseList(String userId);
	
	void modifyPurchase(String purchaseId);
	
	void deletePurchase(String purchaseId);
	
	void updateAddress(String purchaseId); //*JoinService에도 있는 메소드
	
	void confirmPurchase(String purchaseId, int status);
}

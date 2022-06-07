package com.example.SOMusic.dao;

import com.example.SOMusic.domain.Purchase;

public interface PurchaseDao {
	
	void createPurchase(Purchase purchase);
	
	void updatePurchase(Purchase purchase);
}

package com.example.SOMusic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SOMusic.domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {

	Purchase findPurchaseByPurchaseId(int purchaseId);
	
}

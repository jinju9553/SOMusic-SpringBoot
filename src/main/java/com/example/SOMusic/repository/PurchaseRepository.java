package com.example.SOMusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	Purchase findPurchaseByPurchaseId(int purchaseId);

	void deleteByPurchaseId(int purchaseId);

	Purchase findPurchaseByConsumerId(String consumerId);

	List<Purchase> findAllByConsumerId(String consumerId);
}

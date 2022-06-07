package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SOMusic.domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	Purchase findPurchaseByPurchaseId(int purchaseId);

	void deleteByPurchaseId(String purchaseId);

	Purchase findPurchaseByConsumerId(String consumerId);

	List<Purchase> findAllByConsumerId(String consumerId);
	
	@Query("update purchase " + 
			"set status = :status " +
			"where purchaseId = :purchaseId")
	@Transactional
	public void confirmPurchase(@Param("purchaseId")String purchaseId, @Param("status") int status);
}

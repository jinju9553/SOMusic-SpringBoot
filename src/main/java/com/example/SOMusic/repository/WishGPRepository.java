package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.WishGroupPurchase;

public interface WishGPRepository extends JpaRepository<WishGroupPurchase, Integer> {

	public WishGroupPurchase findByUserIdAndGpId(String userId, int gpId);
	
	@Transactional
	public void deleteByUserIdAndGpId(String userId, int gpId);
	
	public List<WishGroupPurchase> findByUserId(String userId);
	
}

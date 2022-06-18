package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.WishGroupPurchase;

public interface WishGPRepository extends JpaRepository<WishGroupPurchase, Integer> {

	public WishGroupPurchase findByUserIdAndGpId(String userId, int gpId);	// 위시 검색
	
	@Transactional
	public void deleteByUserIdAndGpId(String userId, int gpId);		// 위시 삭제
	
	public List<WishGroupPurchase> findByUserId(String userId);		// 위시 리스트 불러오기
	
}

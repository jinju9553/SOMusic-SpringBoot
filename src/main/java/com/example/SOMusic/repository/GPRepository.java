package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.GroupPurchase;

public interface GPRepository extends JpaRepository<GroupPurchase, Integer> {

	public GroupPurchase findByGpId(Integer id);
	
	public List<GroupPurchase> findBySellerId(String sellerId);
	
	@Transactional
	public void deleteByGpId(int id);
	
	public List<GroupPurchase> findFirst4ByOrderByGpId();	//메인에 보여줄 4개에 공구
	
	public List<GroupPurchase> findAll();	// 모든 공구 불러오기
	
	public List<GroupPurchase> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(String keyword1, String keyword2, String keyword3);		// 공구 검색
}

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
}

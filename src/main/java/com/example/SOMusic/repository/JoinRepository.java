package com.example.SOMusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.Join;

public interface JoinRepository extends JpaRepository<Join, Integer> {

	Join findJoinByJoinId(int joinId);

	List<Join> findAllByConsumerId(String consumerId);
	
	List<Join> findAllByGroupPurchaseGpId(int gpId);

	void deleteByJoinId(int joinId);
	
}

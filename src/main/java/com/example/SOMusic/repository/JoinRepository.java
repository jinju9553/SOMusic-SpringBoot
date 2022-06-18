package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SOMusic.domain.Join;

public interface JoinRepository extends JpaRepository<Join, Integer> {

	Join findJoinByJoinId(int joinId);

	List<Join> findAllByConsumerId(String consumerId);
	
	List<Join> findAllByGroupPurchaseGpId(int gpId);

	void deleteByJoinId(String joinId);
	
}

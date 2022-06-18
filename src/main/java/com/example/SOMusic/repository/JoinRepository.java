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

	void deleteByJoinId(String joinId);
	
//	@Transactional
//	@Query(value = "update join set status = :status where grouppurchase_id = :gpId", nativeQuery = true)
//	void updateAllStatus(@Param("gpId") int gpId, @Param("status") int status);
	
//	@Transactional
//	@Query("update Join set status = :status where groupPurchase.gpId = :gpId")
//	void updateAllStatus(@Param("gpId") int gpId, @Param("status") int status);
	
}

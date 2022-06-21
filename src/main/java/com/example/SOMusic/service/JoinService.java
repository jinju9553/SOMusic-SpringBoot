package com.example.SOMusic.service;

import java.util.List;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;

public interface JoinService {
	
	void registerJoin(Join join);
	
	void modifyJoin(Join join);
	
	void deleteJoin(int joinId);

	//void updateStatus(String joinId);
	
	/* Spring Data JPA */
	Join findJoinByJoinId(int joinId);
	
	List<Join> findAllByUserId(String userId);
	
	List<Join> findAllByGroupPurchaseGpId(int gpId);

	int calculateTotal(GroupPurchase groupPurchase, Join join);

	int initShippingCost(Join join);

	int updateTotal(Join join, int newShippingCost);
	
	void updateAllStatus(int gpId, int status);
	
	public void updateStatus(int joinId, int status);
}

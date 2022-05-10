package com.example.SOMusic.service;

import java.util.List;
import com.example.SOMusic.domain.Join;

public interface JoinService {

	//접근제어: default
	void createJoin(Join join);
	
	Join findJoin(String userId);
	
	List<Join> findJoinList(String userId);
	
	void modifyJoin(String joinId);
	
	void deleteJoin(String joinId);
	
	void updateAddress(String joinId); //*PurchaseService에도 있는 메소드
	
	void updateStatus(String joinId);
}

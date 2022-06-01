package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.controller.PurchaseRequest;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.repository.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Override
	public Purchase registerPurchase(PurchaseRequest purchaseReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Purchase findPurchaseByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Purchase> findPurchaseList(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyPurchase(String purchaseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePurchase(String purchaseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAddress(String purchaseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmPurchase(String purchaseId, int status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Purchase findPurchaseByPurchaseId(int purchaseId) {
		return purchaseRepository.findPurchaseByPurchaseId(purchaseId);
	}

}

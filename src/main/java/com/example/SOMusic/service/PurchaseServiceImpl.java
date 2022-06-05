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
		//DAO에서 구현
		return null;
	}

	@Override
	public Purchase findPurchaseByUserId(String consumerId) {
		return purchaseRepository.findPurchaseByConsumerId(consumerId);
	}

	@Override
	public List<Purchase> findPurchaseList(String consumerId) {
		return purchaseRepository.findAllByConsumerId(consumerId);
	}

	@Override
	public void modifyPurchase(String purchaseId) {
		//DAO에서 구현
		
	}

	@Override
	public void deletePurchase(String purchaseId) {
		purchaseRepository.deleteByPurchaseId(purchaseId);
	}

	@Override
	public void confirmPurchase(String purchaseId, int status) {
		//DAO에서 구현
		
	}

	@Override
	public Purchase findPurchaseByPurchaseId(int purchaseId) {
		return purchaseRepository.findPurchaseByPurchaseId(purchaseId);
	}

}

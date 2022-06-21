package com.example.SOMusic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.PurchaseDao;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.repository.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private PurchaseDao purchaseDao;
	
	@Override
	public void registerPurchase(Purchase purchase) {
		purchaseDao.createPurchase(purchase);
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
	public void modifyPurchase(Purchase purchase) {
		purchaseDao.updatePurchase(purchase);
	}
	
	@Override
	@Transactional
	public void modifyPurchaseInfo(int purchaseId, Purchase purchaseReq) {
		Purchase purchase = this.findPurchaseByPurchaseId(purchaseId);
		
		purchase.setConsumerName(purchaseReq.getConsumerName());
		purchase.setPhone(purchaseReq.getPhone());
		purchase.setZipcode(purchaseReq.getZipcode());
		purchase.setAddress(purchaseReq.getAddress());
		purchase.setShippingRequest(purchaseReq.getShippingRequest());
	}

	@Override
	@Transactional
	public void deletePurchase(int purchaseId) {
		purchaseRepository.deleteByPurchaseId(purchaseId);
	}

	@Override
	public Purchase findPurchaseByPurchaseId(int purchaseId) {
		return purchaseRepository.findPurchaseByPurchaseId(purchaseId);
	}

	@Override
	public int calculateTotal(Product product) {
		return (product.getPrice() + product.getShippingCost());
	}

	@Override
	@Transactional
	public void confirmPurchase(int purchaseId) {
		Purchase p = this.findPurchaseByPurchaseId(purchaseId);
		if(p.getProduct().getStatus() == 0) { //0: 승인 전
			p.getProduct().setStatus(1); //1: 승인됨
			p.setStatus(1); 
		}
	}
}

package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.controller.GPRequest;
import com.example.SOMusic.dao.GPDao;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.repository.GPRepository;

@Service
public class GPService {
	
	@Autowired
	private GPDao dao;
	
	@Autowired
	private GPRepository gpRepository;
	
	public void insertGP(GroupPurchase gp) {
		dao.insertGP(gp);
	}
	
	public void updateGP(GroupPurchase gp) {
		dao.updateGP(gp);
	}
	
	public void deleteGP(int gpId) {
		gpRepository.deleteByGpId(gpId);
	}
	
	public GroupPurchase getGP(int gpId) {
		return gpRepository.findByGpId(gpId);
	}
	
	public List<GroupPurchase> getMyGPList(String sellerId) {
		return gpRepository.findBySellerId(sellerId);
	}
	
	public List<GroupPurchase> get4GPList() {
		return gpRepository.findFirst4ByOrderByGpId();
	}
	
	public List<GroupPurchase> getAllGPList() {
		return gpRepository.findAll();
	}
	
}

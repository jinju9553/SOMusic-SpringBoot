package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.controller.GPRequest;
import com.example.SOMusic.dao.GPDao;
import com.example.SOMusic.repository.GPRepository;

@Service
public class GPService {
	
	@Autowired
	private GPDao dao;
	
	@Autowired
	private GPRepository gpRepository;
	
	public void insertGP(GPRequest gp) {
		dao.insertGP(gp);
	}
	
	public GPRequest getGP(int gpId) {
		return gpRepository.findByGpId(gpId);
	}
	
	public List<GPRequest> getMyGPList(String sellerId) {
		return gpRepository.findBySellerId(sellerId);
	}
	
}

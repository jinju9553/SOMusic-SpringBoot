package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.controller.GPRequest;
import com.example.SOMusic.dao.GPDao;
import com.example.SOMusic.dao.WishGPDAO;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.PKWishGP;
import com.example.SOMusic.domain.WishGroupPurchase;
import com.example.SOMusic.repository.GPRepository;
import com.example.SOMusic.repository.WishGPRepository;

@Service
public class GPService {
	
	@Autowired
	private GPDao dao;
	
	@Autowired
	private GPRepository gpRepository;
	
	@Autowired
	private WishGPRepository wishGgpRepository;
	
	@Autowired
	private WishGPDAO wishDao;
	
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
	
	public List<GroupPurchase> get4GPList() {		// 메인에 보여줄 4개의 공구
		return gpRepository.findFirst4ByOrderByGpId();
	}
	
	public List<GroupPurchase> getAllGPList() {		// 모든 공구
		return gpRepository.findAll();
	}
	
	public List<GroupPurchase> getSearchGPList(String keyword) {		// 검색
		return gpRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword, keyword);
	}
	
	public void insertWishGP(String userId, int gpId) {		// 위시 추가
		WishGroupPurchase wish = new WishGroupPurchase(userId, gpId);
		wishDao.insertWishGP(wish);
	}
	
	public WishGroupPurchase getWishGP(String userId, int gpId) {	// 위시 검색
		return wishGgpRepository.findByUserIdAndGpId(userId, gpId);
	}
	
	public void deleteWishGP(String userId, int gpId) {		// 위시 삭제
		wishGgpRepository.deleteByUserIdAndGpId(userId, gpId);
	}
	
	public List<WishGroupPurchase> getWishGPList(String userId) {		// 위시리스트 불러오기
		return wishGgpRepository.findByUserId(userId);
	}
	
}

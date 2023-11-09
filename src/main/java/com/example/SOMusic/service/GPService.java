package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.GPDao;
import com.example.SOMusic.dao.WishGPDao;
import com.example.SOMusic.domain.GroupPurchase;
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
	private WishGPRepository wishGpRepository;
	
	@Autowired
	private WishGPDao wishDao;
	
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
	
	public List<GroupPurchase> getMainGPList() {
		return gpRepository.findFirst4ByOrderByGpId();
	}
	
	public List<GroupPurchase> getAllGPList() {
		return gpRepository.findAll();
	}
	
	public List<GroupPurchase> getSearchGPList(String keyword) {
		return gpRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(
				keyword, keyword, keyword);
	}
	
	public void insertWishGP(String userId, int gpId) {
		WishGroupPurchase wish = new WishGroupPurchase(userId, gpId);
		wishDao.insertWishGP(wish);
	}
	
	public WishGroupPurchase getWishGP(String userId, int gpId) {
		return wishGpRepository.findByUserIdAndGpId(userId, gpId);
	}
	
	public void deleteWishGP(String userId, int gpId) {
		wishGpRepository.deleteByUserIdAndGpId(userId, gpId);
	}
	
	public List<WishGroupPurchase> getWishGPList(String userId) {
		return wishGpRepository.findByUserId(userId);
	}
	
}

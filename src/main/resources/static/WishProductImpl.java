package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.WishlistDao;
import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.repository.WishProductRepository;

@Service
public class WishProductImpl implements WishProductService {

	@Autowired
	private WishlistDao wishDao;
	
	@Autowired
	private WishProductRepository wpRepo;
	
	@Override
	public void addWishproduct(WishProduct wishproduct) {
		wishDao.addWish(wishproduct);	
	}

	@Override
	public List<WishProduct> findWishProductList(String userId) {
		return wpRepo.findByUserId(userId);
	}
	
	@Override
	public List<WishProduct> isinterested(String userId, int productId) {
		return wpRepo.getWishProductByproductIdAndUserId(userId, productId);
	}

	@Override
	public void deleteWishproduct(String userId, int productId) {
		// TODO Auto-generated method stub
		
	}


	
}

package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.SOMusic.dao.WishProductDao; //JPetStore에는 Dao가
//존재하는데 솜뮤직에 생성해도 되는건지
import com.example.SOMusic.domain.WishProduct;

public class WishProductImpl implements WishProductService {

	//DAO 만들고 
	private WishproductDAO wishproductDAO;
	
	@Override
	public void addWishproduct(WishProduct wishproduct) {
		
		wishproductDAO.add(wishProduct); 
	}

	@Override
	public void deleteWishproduct(String userId, String productId) {
		
		
	}

	@Override
	public List<WishProduct> findWishProductList(String userId) {
		
		return null;
	}
	
}

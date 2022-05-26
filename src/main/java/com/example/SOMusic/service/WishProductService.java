package com.example.SOMusic.service;

import java.util.List;
import com.example.SOMusic.domain.WishProduct;

public interface WishProductService {
	
	//void addWishproduct(String userId, String productId);
	
	void deleteWishproduct(String userId, String productId);
	
	public List<WishProduct> findWishProductList(String userId);

	void addWishproduct(WishProduct wishproduct);
	


}

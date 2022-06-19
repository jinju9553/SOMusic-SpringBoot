package com.example.SOMusic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SOMusic.domain.WishProduct;


public interface WishProductService {
	
	void deleteWishproduct(String userId, int productId);
	
	public List<WishProduct> findWishProductList(String userId);

	void addWishproduct(WishProduct wishproduct);
	
	public List<WishProduct> isinterested(String userId, int productId);
	
}

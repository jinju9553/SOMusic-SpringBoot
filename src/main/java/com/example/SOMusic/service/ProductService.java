package com.example.SOMusic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.WishProduct;

@Service
public interface ProductService {
	
	void addProduct(Product product);
	
	void deleteProduct(int productId);
	
	void updateProduct(Product product);
	
	List<Product> getMyPrList(String sellerId);

	Product findProductByProductId(int productId);
	
	List<Product> getMainProductList();
	
	List<Product> getAllProductList();
	
	public List<Product> getSearchProductList(String keyword);
	
	// Wish Product
	
	void deleteWishproduct(String userId, int productId);
	
	public List<WishProduct> findWishProductList(String userId);

	void addWishproduct(WishProduct wishproduct);
	
	public WishProduct getWishPr(String userId, int productId);
		
}

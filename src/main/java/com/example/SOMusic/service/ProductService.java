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

	public Product getPr(int productId);
	
	List<Product> get4ProductList();	// 메인에 보여줄 4개 상품
	
	List<Product> getAllProductList();
	
	public List<Product> getSearchProductList(String keyword);		// 검색
	
	// Wish Product
	
	void deleteWishproduct(String userId, int productId);
	
	public List<WishProduct> findWishProductList(String userId);

	void addWishproduct(WishProduct wishproduct);
	
	public List<WishProduct> isinterested(String userId, int productId);
	
	public WishProduct getWishPr(String userId, int productId);
		
}

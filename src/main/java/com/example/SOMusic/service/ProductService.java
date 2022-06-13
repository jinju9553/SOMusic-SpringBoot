package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.ProductDao;
import com.example.SOMusic.domain.Product;

@Service
public interface ProductService {
	
	void addProduct(Product product);
	
	void deleteProduct(int productId);
	
	void updateProduct(Product product);
	
	List<Product> getMyPrList(String sellerId);

	/* Spring Data JPA */
	Product findProductByProductId(int productId);

	
	List<Product> get4ProductList();	// 메인에 보여줄 4개 상품
	
	List<Product> getAllProductList();
	
	public List<Product> getSearchProductList(String keyword);		// 검색
		
}

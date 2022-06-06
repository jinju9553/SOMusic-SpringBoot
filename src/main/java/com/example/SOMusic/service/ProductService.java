package com.example.SOMusic.service;

import org.springframework.stereotype.Service;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;

@Service
public interface ProductService {
	
	void addProduct(int ProductId, String userId);
	
	void deleteProduct(int ProductId, String userId);
	
	void updateProduct(int ProductId, String userId);

	/* Spring Data JPA */
	Product findProductByProductId(int productId);
	
}

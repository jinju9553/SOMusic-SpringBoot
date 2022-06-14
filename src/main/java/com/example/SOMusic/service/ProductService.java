package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.ProductDao;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;

@Service
public interface ProductService {
	
	void addProduct(Product product);
	
	void deleteProduct(int productId);
	
	void updateProduct(Product product);
	
	List<Product> getMyPrList(String sellerId);

	Product findProductByProductId(int productId);

	
}

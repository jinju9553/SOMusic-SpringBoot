package com.example.SOMusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SOMusic.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	Product findProductByProductId(int productId);
	
	public List<Product> findProductBySellerId(String sellerId);
	
	public void deleteByProductId(int productId);
}

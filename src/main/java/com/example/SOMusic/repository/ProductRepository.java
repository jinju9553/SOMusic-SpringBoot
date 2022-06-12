package com.example.SOMusic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SOMusic.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	Product findProductByProductId(int productId);
	
	void deleteByProductId(int productId);
}

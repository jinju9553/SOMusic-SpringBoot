package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.repository.ProductRepository;
import com.example.SOMusic.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void addProduct(int ProductId, String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(int ProductId, String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(int ProductId, String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product findProductByProductId(int productId) {
		return productRepository.findProductByProductId(productId);
	}

}

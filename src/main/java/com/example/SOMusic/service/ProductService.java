package com.example.SOMusic.service;

import org.springframework.stereotype.Service;
import com.example.SOMusic.domain.Product;

@Service
public interface ProductService {
	
	void addProduct(String ProductId, String UserId);
}

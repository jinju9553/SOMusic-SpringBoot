package com.example.SOMusic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.repository.ProductRepository;
import com.example.SOMusic.dao.ProductDao;
import com.example.SOMusic.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductDao dao;
	
	
	@Override
	public void addProduct(Product product) {
		dao.createProduct(product);
		
	}

	@Override
	public void deleteProduct(int productId) {
		productRepository.deleteByProductId(productId);
		
	}

	@Override
	public void updateProduct(Product product) {
		dao.updateProduct(product);
		
	}

	@Override
	public Product findProductByProductId(int productId) {
		return productRepository.findProductByProductId(productId);
	}
	
	@Override
	public List<Product> getMyPrList(String sellerId){
		return productRepository.findProductBySellerId(sellerId);
	}

	public List<Product> get4ProductList() {
		return productRepository.findFirst4ByOrderByProductId();
	}
	
	@Override
	public List<Product> getAllProductList() {
		return productRepository.findAll();
	}
	
	@Override
	public List<Product> getSearchProductList(String keyword) {
		return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrArtistNameContainingIgnoreCase(keyword, keyword, keyword);
	}

}

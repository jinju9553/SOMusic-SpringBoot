package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	Product findProductByProductId(int productId);
	
	public List<Product> findProductBySellerId(String sellerId);
	
	@Transactional
	public void deleteByProductId(int productId);

	public List<Product> findFirst4ByOrderByProductId();	//메인에 보여줄 4개에 상품
	
	public List<Product> findAll();		// 모든 상품
	
	public List<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrArtistNameContainingIgnoreCase(String keyword1, String keyword2, String keyword3);		// 상품 검색

}

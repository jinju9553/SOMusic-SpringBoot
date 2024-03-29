package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.WishProduct;

public interface WishProductRepository extends JpaRepository<WishProduct, String>{

	 @Transactional 
	 public void deleteByproductIdAndUserId(int productId, String userId);
	 
	//상품 위시 검색
	public WishProduct findByUserIdAndProductId(String userId, int productId);
	
	//wishlist불러오기 
	public List<WishProduct> findByUserId(String userId);

	
}

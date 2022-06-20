package com.example.SOMusic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.WishProduct;

public interface WishProductRepository extends JpaRepository<WishProduct, String>{

	 @Transactional 
	 public void deleteByproductIdAndUserId(int productId, String userId);
	 
	
	//wishlist불러오기 
	public List<WishProduct> findByUserId(String userId);
	
	
	//wish 했는지 안했는지 
	public List<WishProduct> getWishProductByproductIdAndUserId(String userId, int productId);
	
}

package com.example.SOMusic.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.SOMusic.dao.ProductDao;
import com.example.SOMusic.dao.WishProudctDao;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.repository.WishProductRepository;

@ExtendWith(SpringExtension.class)
public class WIshServiceTest {
	
	@Mock
	WishProudctDao dao;
	@Mock
	ProductDao prdao;
	
	@InjectMocks
	ProductServiceImpl prSvc;
	
	@Mock
	WishProductRepository Repo;	
	
	@Test
	@DisplayName("addWish")
	void add() {
		Product pr = new Product();
		pr.setProductName("scarlet");
		prSvc.addProduct(pr);
		
		WishProduct wishpr = new WishProduct();
		
		wishpr.setProductId(pr.getProductId());
		wishpr.setUserId("123");
		
		prSvc.addWishproduct(wishpr);
		
		assertEquals(pr.getProductId(), wishpr.getProductId());
	
	}
	
	@Test
	@DisplayName("deleteWish")
	void delete() {
		Product pr = new Product();
		pr.setProductName("scarlet");
		prSvc.addProduct(pr);
		
		WishProduct wishpr = new WishProduct();
		
		wishpr.setProductId(pr.getProductId());
		wishpr.setUserId("123");
		
		prSvc.addWishproduct(wishpr);
		
		prSvc.deleteWishproduct("123", wishpr.getProductId());
		
		List<WishProduct> result = prSvc.findWishProductList("123");
		assertEquals(result.size(), 0);
		
	
	}
}

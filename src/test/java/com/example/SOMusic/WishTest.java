package com.example.SOMusic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.ProductService;


@SpringBootTest
class WishTest {
	@Autowired
	ProductService prSvc;
	
	@Autowired
	AccountService acSvc;
	
	@Test
	@DisplayName("Wish 추가 테스트")
	void createWish() {
		Product pr = new Product();
		WishProduct wish = new WishProduct();
		Account acc = new Account();
		
		
		pr.setAccount(9999);
		pr.setArtistName("9999");
		pr.setPrice(9999);

		acc.setUserId("abc");
		acc.setPassword("abc");
		
		prSvc.addProduct(pr);
		
		wish.setProductId(pr.getProductId());
		wish.setUserId(acc.getUserId());
	

		prSvc.addWishproduct(wish);
		
	}
	
	@Test
	@DisplayName("Wish 삭제 테스트")
	void deleteWish() {
		Product pr = new Product();
		WishProduct wish = new WishProduct();
		Account acc = new Account();
		
		
		//given
		pr.setAccount(9999);
		pr.setArtistName("9999");
		pr.setPrice(9999);

		acc.setUserId("abc");
		acc.setPassword("abc");
		
		prSvc.addProduct(pr);
		
		wish.setProductId(pr.getProductId());
		wish.setUserId(acc.getUserId());
	
		prSvc.addWishproduct(wish);
		
		//when
		prSvc.deleteWishproduct(acc.getUserId(), pr.getProductId());
		
	}	
	

}

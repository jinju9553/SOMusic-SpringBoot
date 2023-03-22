package com.example.SOMusic.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.domain.Product;

@DataJpaTest
public class WishProductRepositoryTest {
	
	@Autowired
	ProductRepository PrRepo;
	
	@Autowired
	WishProductRepository wishPrRepo;
	
	
	@Test
	@DisplayName("add WishProduct")
	void wishPrcrate() {
		Product pr = new Product();
		pr.setSellerId("123");
		pr.setProductId(123);
		
		Product result =  PrRepo.save(pr);
		
		WishProduct wishpr = new WishProduct();
		wishpr.setProductId(result.getProductId());
		wishpr.setUserId("123");
		
		wishpr.setPr(pr);
		
		assertEquals(wishpr.getPr().getSellerId(), "123");	
	}
	
	@Test
	@DisplayName("findProductByProductId")
	void findWishPr() {

		Product pr1 = new Product();
		pr1.setSellerId("123");
		
		Product pr2 = new Product();
		pr2.setSellerId("123");
		
		Product prResult1 = PrRepo.save(pr1);
		Product prResult2 = PrRepo.save(pr2);	
		
		WishProduct w1 = new WishProduct("123", prResult1.getProductId());
		WishProduct w2 = new WishProduct("123", prResult2.getProductId());
		
		w1.setPr(pr1);
		w2.setPr(pr2);
		
		wishPrRepo.save(w1);
		wishPrRepo.save(w2);
		
		Product last = PrRepo.findProductByProductId(prResult1.getProductId());
		
		assertEquals(last.getProductId(),prResult1.getProductId());
	}
	
	@Test
	@DisplayName("delete WishProduct")
	void deleteWishPr() {
		
		Product pr1 = new Product();
		pr1.setSellerId("123");
		
		Product pr2 = new Product();
		pr2.setSellerId("123");
		
		
		Product prRslt1 = PrRepo.save(pr1);
		Product prRslt2 = PrRepo.save(pr2);
		
		WishProduct w1 = new WishProduct("123", prRslt1.getProductId());
		WishProduct w2 = new WishProduct("123", prRslt2.getProductId());
		
		w1.setPr(pr1);
		w2.setPr(pr2);
		
		wishPrRepo.save(w1);
		wishPrRepo.save(w2);
		
		List<WishProduct> result = wishPrRepo.findByUserId("123");
		assertEquals(result.size(), 2);
		
		wishPrRepo.deleteByproductIdAndUserId(prRslt1.getProductId(), "123");
		
		List<WishProduct> result2 = wishPrRepo.findByUserId("123");
		assertEquals(result2.size(), 1);
		
	}

}

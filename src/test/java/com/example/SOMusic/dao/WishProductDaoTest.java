package com.example.SOMusic.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.WishProduct;

@DataJpaTest
class WishProductDaoTest {
	
	@MockBean
	WishProudctDao dao;
	
	@MockBean
	ProductDao prdao;
	
	@Test
	@DisplayName("addWish")
	void add() {
		Product product = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "s", "s", "s");
		prdao.createProduct(product);

		WishProduct wishpr = new WishProduct();
		
		wishpr.setProductId(product.getProductId());
		wishpr.setUserId("123");
		
		dao.addWish(wishpr);
		
		assertEquals(product.getProductId(), wishpr.getProductId());
		
	}
	

}

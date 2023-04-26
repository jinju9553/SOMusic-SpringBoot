package com.example.SOMusic.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.SOMusic.domain.Product;

@DataJpaTest
class ProductDaoTest {
	
	@MockBean
	ProductDao dao;
	
	@Test
	@DisplayName("insert")
	void insert() {
		
		Product product = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "s", "s", "s");
		
		dao.createProduct(product);
		
		assertEquals("scarlet", product.getProductName());
		
	}
	
	@Test
	@DisplayName("merge")
	void merge() {
		
		Product product = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "s", "s", "s");
		
		dao.createProduct(product);
		
		product.setProductName("violet");
		dao.updateProduct(product);
		
		assertEquals("violet", product.getProductName());
		
	}

}

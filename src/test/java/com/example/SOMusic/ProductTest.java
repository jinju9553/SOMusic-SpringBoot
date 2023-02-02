package com.example.SOMusic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.ProductService;


@SpringBootTest
class ProductTest {
	@Autowired
	ProductService prSvc;
	
	@Test
	@DisplayName("Product 생성 테스트")
	void createProduct() {
		Product pr = new Product();	
	
		pr.setAccount(9999);
		pr.setArtistName("9999");
		pr.setPrice(9999);
	
		prSvc.addProduct(pr);
				
	
	}
	
	@Test
	@DisplayName("Product 삭제 테스트")
	void deleteProduct() {
		Product pr = new Product();
		
		pr.setProductId(2);

		prSvc.deleteProduct(2);
		

		
	}

}

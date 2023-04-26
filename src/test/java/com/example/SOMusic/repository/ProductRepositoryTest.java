package com.example.SOMusic.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.repository.ProductRepository;


@DataJpaTest
class ProductRepositoryTest {
	
	@Autowired
	ProductRepository prRepo;
	
	@After
	public void cleanup() {
		prRepo.deleteAll();
	}
	
	@Test
	@DisplayName("save Product")
	void save() {
		Product pr = new Product();
		pr.setProductName("123");
		
		Product result = prRepo.save(pr);
		
		assertEquals(result.getProductName(), pr.getProductName());
	}
	
	@Test
	@DisplayName("ProductfindAll")
	void ProductfindAll() {
		
		Product pr1 = new Product();
		pr1.setProductName("scarlet");
		
		Product pr2 = new Product();
		pr2.setProductName("violet");
		
		prRepo.save(pr1);
		prRepo.save(pr2);
		
		List<Product> result2 = prRepo.findAll();
		
		assertEquals(result2.size(), 2);
	}
	
	@Test
	@DisplayName("findProductByProductId")
	void findbyProductId() {
		Product pr1 = new Product();
		pr1.setProductName("scarlet");
		
		Product pr2 = new Product();
		pr2.setProductName("violet");
		
		Product prResult = prRepo.save(pr1);
		prRepo.save(pr2);
		
		Product result = prRepo.findProductByProductId(prResult.getProductId());
		
		assertEquals((result.getProductId()), pr1.getProductId());
	}
	
	//추후 확인
	@Test
	@DisplayName("findBySellerId")
	void findBySellerId() {
		
		Product pr1 = new Product();
		pr1.setProductName("scarlet");
		pr1.setSellerId("123");
		
		Product pr2 = new Product();
		pr2.setProductName("violet");
		pr2.setSellerId("123");
		
		Product pr3 = new Product();
		pr2.setProductName("scarlet2");
		pr2.setSellerId("124");
		
		prRepo.save(pr1);
		prRepo.save(pr2);
		prRepo.save(pr3);
		
		List<Product> result = prRepo.findProductBySellerId("123");
		
		assertEquals(result.size(), 2);
	}
	

	
	@Test
	@DisplayName("delete")
	void delete() {	
		Product pr1 = new Product();
		pr1.setProductName("scarlet");
		pr1.setSellerId("123");
		
		Product pr2 = new Product();
		pr2.setProductName("violet");
		pr2.setSellerId("123");		
		
		Product prResult = prRepo.save(pr1);
		prRepo.save(pr2);
		
		prRepo.deleteByProductId(prResult.getProductId());
		
		List<Product> rslt = prRepo.findAll();
		
		assertEquals(rslt.size(), 1);
	
		
	}
	


}

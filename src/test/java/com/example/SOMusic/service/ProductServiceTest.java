package com.example.SOMusic.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.Mockito;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.SOMusic.dao.ProductDao;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.repository.ProductRepository;


@ExtendWith(SpringExtension.class)
class ProductServiceTest {
	
	@Mock
	ProductDao dao;
	
	@InjectMocks
	ProductServiceImpl prSvc;
	
	@Mock
	ProductRepository prRepo;
	
	
	@Test
	@DisplayName("addProduct")
	void add() {
		Product pr = new Product();
		pr.setProductName("scarlet");
		
		prSvc.addProduct(pr);
		
		assertEquals("scarlet", pr.getProductName());
		
	}
	
	@Test
	@DisplayName("deleteProduct")
	void delete() {
		Product pr = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "s", "s", "s");
		
		prSvc.addProduct(pr);
		prSvc.deleteProduct(pr.getProductId());
		
		List<Product> rslt = prSvc.getAllProductList();
		assertEquals(rslt.size(), 0);
	}
	
	@Test
	@DisplayName("updateProduct")
	void update() {
		Product pr = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "s", "s", "s");
		
		prSvc.addProduct(pr);
		pr.setProductName("violet");
		prSvc.updateProduct(pr);
		
		assertEquals("violet", pr.getProductName());
	}
	
	@Test
	@DisplayName("getMyPrList")
	void getMyPrList() {
		Product pr1 = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "bomin", "s", "s");		
		
		Product pr2 = new Product("violet", 123, "s", "s", 123, 123,
				123, 123, "s", "bomin", "s", "s");
		
		List<Product> prList = new ArrayList<>();
		prList.add(pr1);
		prList.add(pr2);
		
		Mockito.when(prRepo.findProductBySellerId("bomin")).thenReturn(prList);
		
		List<Product> result = prSvc.getMyPrList("bomin");
		
		assertEquals(result.size(), 2);
	
	}
	

}

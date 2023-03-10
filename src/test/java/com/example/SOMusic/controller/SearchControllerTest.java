package com.example.SOMusic.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.ProductService;

@WebMvcTest(SearchController.class)
class SearchControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	GPService gpSvc;
	
	@MockBean
	ProductService prSvc;

	@Test
	@DisplayName("상품 검색")
	void prSearch() throws Exception {

		List<Product> prList = new ArrayList<>();
		Product pr = new Product();
		pr.setProductName("aabb");
		prList.add(pr);
		
		String keyword = "aa";
		
		Mockito.when(prSvc.getSearchProductList(keyword)).thenReturn(prList);
		
		mvc.perform(MockMvcRequestBuilders.get("/main/product/search").param("keyword", keyword))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.model().attribute("productList", prList))
			.andExpect(MockMvcResultMatchers.model().attribute("keyword", keyword));
		
	}
	
	@Test
	@DisplayName("공구 검색")
	void gpSearch() throws Exception {

		List<GroupPurchase> gpList = gpSvc.get4GPList();
		GroupPurchase gp = new GroupPurchase();
		gp.setTitle("aaa");
		gpList.add(gp);
		
		String keyword = "aa";
		
		Mockito.when(gpSvc.getSearchGPList(keyword)).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/main/gp/search").param("keyword", keyword))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.model().attribute("gpList", gpList))
			.andExpect(MockMvcResultMatchers.model().attribute("keyword", keyword));
		
	}

}

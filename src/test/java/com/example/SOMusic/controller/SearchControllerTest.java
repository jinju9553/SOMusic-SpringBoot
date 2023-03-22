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
	
	private static final String GP_SEARCH = "thyme/search/GPSearchList";
	private static final String PRODUCT_SEARCH = "thyme/search/ProductSearchList";

	@Test
	@DisplayName("상품 검색")
	public void prSearch() throws Exception {

		List<Product> prList = getPrList();
		String keyword = "aa";
		
		Mockito.when(prSvc.getSearchProductList(keyword)).thenReturn(prList);
		
		mvc.perform(MockMvcRequestBuilders.get("/main/product/search").param("keyword", keyword))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(PRODUCT_SEARCH))
			.andExpect(MockMvcResultMatchers.model().attribute("productList", prList))
			.andExpect(MockMvcResultMatchers.model().attribute("keyword", keyword));
		
	}
	
	@Test
	@DisplayName("공구 검색")
	public void gpSearch() throws Exception {

		List<GroupPurchase> gpList = getGPList();
		String keyword = "aa";
		
		Mockito.when(gpSvc.getSearchGPList(keyword)).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/main/gp/search").param("keyword", keyword))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(GP_SEARCH))
			.andExpect(MockMvcResultMatchers.model().attribute("gpList", gpList))
			.andExpect(MockMvcResultMatchers.model().attribute("keyword", keyword));
		
	}
	
	public List<Product> getPrList() {
		Product pr1 = new Product();
		pr1.setProductName("pr1");
		
		Product pr2 = new Product();
		pr2.setProductName("pr2");
		
		List<Product> prList = new ArrayList<>();
		prList.add(pr1);
		prList.add(pr2);
		
		return prList;
	}
	
	public List<GroupPurchase> getGPList() {
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("gp1");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("gp2");
		
		List<GroupPurchase> gpList = new ArrayList<>();
		gpList.add(gp1);
		gpList.add(gp2);
		
		return gpList;
	}

}

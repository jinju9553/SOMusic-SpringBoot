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

@WebMvcTest(MainController.class)
class MainControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	GPService gpSvc;
	
	@MockBean
	ProductService prSvc;
	
	private static final String MAIN = "/main";		// '/'가 들어오면 '/main' 실행
	private static final String MAIN_VIEW = "thyme/main";
	private static final String MAIN_GP_VIEW = "thyme/gp/list/GPListView";
	private static final String MAIN_PRODUCT_VIEW = "thyme/Product/list/ProductListView";
	
	@Test
	@DisplayName("루트 메인화면 이동")
	public void rootMain() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.redirectedUrl(MAIN));
		
	}
	
	@Test
	@DisplayName("메인화면 리스트")
	public void main() throws Exception {

		List<Product> prList = getPrList();
		List<GroupPurchase> gpList = getGPList();
		
		Mockito.when(prSvc.get4ProductList()).thenReturn(prList);
		Mockito.when(gpSvc.get4GPList()).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/main"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(MAIN_VIEW))
			.andExpect(MockMvcResultMatchers.model().attribute("gpList", gpList))
			.andExpect(MockMvcResultMatchers.model().attribute("productList", prList));
		
	}
	
	@Test
	@DisplayName("상품 메인 리스트")
	public void prList() throws Exception {

		List<Product> prList = getPrList();
		
		Mockito.when(prSvc.getAllProductList()).thenReturn(prList);
		
		mvc.perform(MockMvcRequestBuilders.get("/main/product"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(MAIN_PRODUCT_VIEW))
			.andExpect(MockMvcResultMatchers.model().attribute("productList", prList));
		
	}
	
	@Test
	@DisplayName("공구 메인 리스트")
	public void gpList() throws Exception {

		List<GroupPurchase> gpList = getGPList();
		
		Mockito.when(gpSvc.getAllGPList()).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/main/gp"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(MAIN_GP_VIEW))
			.andExpect(MockMvcResultMatchers.model().attribute("gpList", gpList));
		
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

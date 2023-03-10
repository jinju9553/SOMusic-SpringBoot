package com.example.SOMusic.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.WishGroupPurchase;
import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.ProductService;

@WebMvcTest(WishController.class)
class WishControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	GPService gpSvc;
	
	@MockBean
	ProductService prSvc;
	
	Login userSession;
	
	MockHttpSession session;
	
	@BeforeEach()
    public void setup() {
		Account account = new Account();
		account.setUserName("hi-he");
		account.setUserId("hi");
		
		userSession = new Login(account);
		
		session = new MockHttpSession();
		session.setAttribute("userSession", userSession);
		
	}

	@Test
	@DisplayName("상품 위시 리스트")
	void wishProductList() throws Exception {
		
		List<WishProduct> prList = new ArrayList<>();
		WishProduct pr = new WishProduct();
		
		Product p = new Product();
		p.setProductName("aaa");
		
		pr.setUserId("hi");
		pr.setPr(p);
		
		prList.add(pr);
		
		Mockito.when(prSvc.findWishProductList("hi")).thenReturn(prList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/product/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("wishPrList", prList));
	}
	
	@Test
	@DisplayName("상품 위시 추가")
	void addWishProduct() throws Exception {
		
		Product p = new Product();
		p.setProductId(1);
		p.setProductName("aaa");
		
		WishProduct wish = new WishProduct();
		wish.setProductId(1);
		wish.setUserId(userSession.getAccount().getUserId());
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/product/add")
										.param("productId", "1").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
		
//		Mockito.verify(prSvc, Mockito.times(1)).addWishproduct(wish);
		
	}
	
	@Test
	@DisplayName("공구 위시 리스트")
	void wishGPList() throws Exception {
		
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		String userId = userSession.getAccount().getUserId();
		
		WishGroupPurchase wish = new WishGroupPurchase(userId, 1);
		wish.setGp(gp);
		
		List<WishGroupPurchase> gpList = new ArrayList<>();
		gpList.add(wish);
		
		Mockito.when(gpSvc.getWishGPList("hi")).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/gp/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("wishGpList", gpList));
	}
		
}

package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.ProductServiceImpl;
import com.example.SOMusic.service.PurchaseServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(ProductViewController.class)
class ProductViewControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	ProductServiceImpl svc;

	@MockBean
	PurchaseServiceImpl purchaseSvc;
	
	Login userSession;

	MockHttpSession session;	
	
	MockHttpServletRequest request;

	@BeforeEach()
    public void setup() {
		Account account = new Account();
		account.setUserName("hi-he");
		account.setUserId("hi");

		userSession = new Login(account);

		session = new MockHttpSession();
		session.setAttribute("userSession", userSession);
		
		request = new MockHttpServletRequest();

	}
	
	@Test
	@DisplayName("showProductView2")
	void showProductView2() throws Exception {
		
		Product pr = pr();
		
		String id = String.valueOf(pr.getProductId());
		int id2 = pr.getProductId();

		Mockito.when(svc.findProductByProductId(id2)).thenReturn(pr);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/product/info")
				.param("productId", id))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("viewPr", pr));		
	}
	
	public Product pr() {
		Product pr = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "hi", "s", "s");
		
		return pr;
	}	

}

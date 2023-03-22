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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(MyProductController.class)
class MyProductControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	ProductServiceImpl svc;

	@MockBean
	PurchaseServiceImpl purchaseSvc;
	
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
	
//	@Test
//	@DisplayName("info")
//	void info() throws Exception {
//		
//		Product pr2 = pr();
//		
//		int id = pr2.getProductId();
//		String id2 = String.valueOf(id);
//		
//		Mockito.when(svc.findProductByProductId(id)).thenReturn(pr2);
//		
//		mvc.perform(MockMvcRequestBuilders.get("/user/my/info")
//				.param("ProductId", id2))
//				.andDo(MockMvcResultHandlers.print())
//				.andExpect(status().isOk())
//				.andExpect(MockMvcResultMatchers.view().name("thyme/Product/View/productView"))
//				.andExpect(MockMvcResultMatchers.model().attribute("pr", pr2));		
//	}
	
	@Test
	@DisplayName("saleList")
	void saleList() throws Exception {
		
		List<Product> prList = prList();
		String id = userSession.getAccount().getUserId();
		
		Mockito.when(svc.getMyPrList(id)).thenReturn(prList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/sale/list")
				.session(session))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("prList", prList));
	
	}
	
	
	@Test
	@DisplayName("registerList")
	void registerList() throws Exception {
		
		List<Purchase> purList = purchaseList();
		String id = userSession.getAccount().getUserId();
		
		Mockito.when(purchaseSvc.findPurchaseList(id)).thenReturn(purList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/purchase/list")
				.session(session))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("pList", purList));
	
	}
	
	
	public List<Product> prList() {
		Product pr1 = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "hi", "s", "s");
		Product pr2 = new Product("violet", 123, "s", "s", 123, 123,
				123, 123, "s", "hi", "s", "s");
		
		List<Product> prList = new ArrayList<>();
		prList.add(pr1);
		prList.add(pr2);
		
		return prList;
	}
	
	public Product pr() {
		Product pr = new Product( "scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "hi", "s", "s");
		
		return pr;
	}
	
	public Purchase purchase() {
		Product pr = pr();
		String id = userSession.getAccount().getUserId();
		
		Purchase purchase = new Purchase();
		purchase.setConsumerId(id);
		purchase.setPurchaseId(1);
		purchase.setProduct(pr);
		
		return purchase;
	}
	
	public List<Purchase> purchaseList() {
		
		Purchase purchase = purchase();
		
		List<Purchase> purList = new ArrayList<>();
		purList.add(purchase);
		
		return purList;
	}
	
	
}

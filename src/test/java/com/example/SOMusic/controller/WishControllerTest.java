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
	
	private static final String WISH_RE = "/product/info/";
	private static final String WISH_PRODUCT_LIST ="thyme/user/my/wish/myWishProductList";
	private static final String WISH_PRODUCT_LIST_URI = "/user/my/wish/product/list";

	private static final String WISH_GP_LIST = "thyme/user/my/wish/myWishGPList";
	private static final String WISH_GP_LIST_URI = "/user/my/wish/gp/list"; // 위시 리스트에서 삭제 -> 위시 리스트로
	private static final String JOIN = "/join/";
	
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
	public void wishProductList() throws Exception {
		
		List<WishProduct> prList = getWishPrList();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.when(prSvc.findWishProductList(userId)).thenReturn(prList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/product/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(WISH_PRODUCT_LIST))
			.andExpect(MockMvcResultMatchers.model().attribute("wishPrList", prList));
	}
	
	@Test
	@DisplayName("상품 위시 추가")
	public void addWishProduct() throws Exception {
		
		WishProduct wish = getWishPr();
		int productId = wish.getProductId();
		
		Mockito.doNothing().when(prSvc).addWishproduct(wish);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/product/add")
										.param("productId", String.valueOf(productId))
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl(WISH_RE + "?productId=" + productId));
		
		Mockito.verify(prSvc).addWishproduct(wish);
		
	}
	
	@Test
	@DisplayName("상품 위시 삭제 - 리스트뷰에서")
	public void deleteWishPrFromListView() throws Exception {
		int productId = getWishPr().getProductId();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.doNothing().when(prSvc).deleteWishproduct(userId, productId);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/product/delete")
				.param("view", "list").param("productId", String.valueOf(productId))
				.session(session))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl(WISH_PRODUCT_LIST_URI));
		
		Mockito.verify(prSvc).deleteWishproduct(userId, productId);
		
	}
	
	@Test
	@DisplayName("상품 위시 삭제 - 뷰에서")
	public void deleteWishPrFromView() throws Exception {
		int productId = getWishPr().getProductId();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.doNothing().when(prSvc).deleteWishproduct(userId, productId);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/product/delete")
				.param("view", "view").param("productId", String.valueOf(productId))
				.session(session))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl(WISH_RE + "?productId=" + productId));
		
		Mockito.verify(prSvc).deleteWishproduct(userId, productId);
		
	}
	
	@Test
	@DisplayName("공구 위시 리스트")
	public void wishGPList() throws Exception {
		
		List<WishGroupPurchase> gpList = getWishGPList();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.when(gpSvc.getWishGPList(userId)).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/gp/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(WISH_GP_LIST))
			.andExpect(MockMvcResultMatchers.model().attribute("wishGpList", gpList));
		
	}
	
	@Test
	@DisplayName("공구 위시 추가")
	public void addWishGP() throws Exception {
		
		WishGroupPurchase wish = getWishGP();
		int gpId = wish.getGpId();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.doNothing().when(gpSvc).insertWishGP(userId, gpId);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/gp/add")
										.param("gpId", String.valueOf(gpId)).session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl(JOIN + gpId));
		
		Mockito.verify(gpSvc).insertWishGP(userId, gpId);
		
	}
	
	@Test
	@DisplayName("공구 위시 삭제 - join form 뷰에서")
	public void deleteWishGPFromJoin() throws Exception {
		int gpId = getWishGP().getGpId();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.doNothing().when(gpSvc).deleteWishGP(userId, gpId);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/gp/delete")
				.param("view", "join").param("gpId", String.valueOf(gpId))
				.session(session))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl(JOIN + gpId));
		
		Mockito.verify(gpSvc).deleteWishGP(userId, gpId);
		
	}
	
	@Test
	@DisplayName("상품 위시 삭제 - 리스트뷰에서")
	public void deleteWishGPFromList() throws Exception {
		int gpId = getWishGP().getGpId();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.doNothing().when(gpSvc).deleteWishGP(userId, gpId);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/wish/gp/delete")
				.param("view", "list").param("gpId", String.valueOf(gpId))
				.session(session))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl(WISH_GP_LIST_URI));
		
		Mockito.verify(gpSvc).deleteWishGP(userId, gpId);
		
	}
	
	public WishProduct getWishPr() {
		Product p = new Product();
		p.setProductId(1);
		p.setProductName("aaa");
		p.setPrice(1000);
		p.setImage("link");
		p.setDescription("It is better");
		p.setCondition(1);
		p.setShippingCost(10);
		p.setStatus(1);
		p.setAccount(33333);
		p.setBank("bank");
		
		String userId = userSession.getAccount().getUserId();
		
		WishProduct wish = new WishProduct();
		wish.setProductId(1);
		wish.setUserId(userId);
		wish.setPr(p);
		
		return wish;
	}
	
	public List<WishProduct> getWishPrList() {
		WishProduct wish = getWishPr();
		
		List<WishProduct> wishList = new ArrayList<>();
		wishList.add(wish);
		
		return wishList;
	}
	
	public WishGroupPurchase getWishGP() {
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is\n good");
		
		String userId = userSession.getAccount().getUserId();
		
		WishGroupPurchase wish = new WishGroupPurchase(userId, 1);
		wish.setGp(gp);
		
		return wish;
	}
	
	public List<WishGroupPurchase> getWishGPList() {
		WishGroupPurchase wish = getWishGP();
		
		List<WishGroupPurchase> wishList = new ArrayList<>();
		wishList.add(wish);
		
		return wishList;
	}
		
}

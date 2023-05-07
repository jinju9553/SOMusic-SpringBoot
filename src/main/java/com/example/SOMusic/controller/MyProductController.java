package com.example.SOMusic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/my")
public class MyProductController {
	
	private static final String MY_PRODUCT_LIST = "thyme/user/my/product/mySaleList";
	private static final String PRODUCT_INFO = "thyme/Product/View/productView";
	private static final String MY_PURCHASE_LIST = "thyme/user/my/purchase/MyPurchaseList";
	
	@Autowired //주의: interface는 class가 아니므로 Bean을 생성할 수 없음	
	private PurchaseService purchaseSvc;
	public void setpurchaseSvc(PurchaseService purchaseSvc) {
		this.purchaseSvc = purchaseSvc;
	}	
	
	@Autowired
	private ProductService productService;
	public void setPrService(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value="/info")
	public String Productinfo(@RequestParam("ProductId") int ProductId, Model model) {
		Product pr = productService.findProductByProductId(ProductId);
		model.addAttribute("pr", pr);
		
		return PRODUCT_INFO;
	}
	

	@GetMapping(value="/sale/list")
	public String saleList(HttpServletRequest request, Model model)
	throws Exception {

		
		Login userSession = getSession(request);
		String sellerId = userSession.getAccount().getUserId();
		
		System.out.println("등록한 상품 리스트 출력중");
		System.out.println("sellerId : " + sellerId);
		
		List<Product> prList = productService.getMyPrList(sellerId);
		model.addAttribute("prList", prList);
		
		System.out.println(prList.toString());
		
		return MY_PRODUCT_LIST; 
		
	}
	

	
	@GetMapping("/purchase/list")
	public String registerList(HttpServletRequest request, Model model) throws Exception {

		Login userSession = getSession(request);
		 
		List<Purchase> pList = purchaseSvc.findPurchaseList(userSession.getAccount().getUserId());
		model.addAttribute("pList", pList);

		System.out.println("구매한 상품 리스트 : " + pList.toString());

		return MY_PURCHASE_LIST;
	}
	
	
	public Login getSession (HttpServletRequest request) {
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		return userSession;
		
	}

	}


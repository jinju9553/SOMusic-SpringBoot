package com.example.SOMusic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.ProductService;

import org.springframework.stereotype.Controller;

@Controller
@SessionAttributes("userSession")
@RequestMapping(value="/product", method = RequestMethod.GET)
public class ProductViewController {

	@Autowired
	private ProductService prSvc;
	public void setProductService(ProductService prSvc) {
		this.prSvc = prSvc;
	}	
	

	@RequestMapping(value="/view", method = RequestMethod.GET) 
	public String showProductView() {
		
		//prSvc.findProductByPurchaseId(productId);
		return "thyme/product/view/productView";
	}
	
	@GetMapping("/info")
	public String showProductView2(
			HttpServletRequest request,
			@RequestParam("productId") int productId, Model model) {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");

		String userId = null;
		WishProduct wishPr = null;
		if(userSession != null) {
			userId = userSession.getAccount().getUserId();
			wishPr = prSvc.getWishPr( userSession.getAccount().getUserId(), productId);
		}
					
		Product viewPr = prSvc.getPr(productId);
		
		System.out.println("상품: " + viewPr);
		System.out.println("판매자아이디: " + viewPr.getSellerId());
		System.out.println("상품 이미지 경로 : " + viewPr.getImage());
		
		model.addAttribute("image", viewPr.getImage());
		model.addAttribute("viewPr", viewPr);
		model.addAttribute("buyerId", userId);
		model.addAttribute("wishPr", wishPr);
				
		return "thyme/Product/View/productView";
	}
}	


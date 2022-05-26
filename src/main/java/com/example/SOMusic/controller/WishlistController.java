package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.WishProductService                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
import com.example.SOMusic.domain.WishGroupPurchase;


@Controller
public class WishlistController {
	
	@Autowired
	private WishProductService wishproductService;
	public void setPurchaseService(WishProductService wishProductService, WishProductService wishproductService) {
		this.wishproductService = wishproductService;
	}

	@RequestMapping("uri넣기")
	public String handleRequest(
			@RequestParam("ProductId") String productId,
			@RequestParam("UserId") String UserId,
			ModelMap model) throws Exception {
		
		return "Wish"; //Wish 객체를리턴해야하나?
	}
	
}

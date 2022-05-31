package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.WishProductService                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
import com.example.SOMusic.domain.WishGroupPurchase;


@Controller
@RequestMapping("/user/my/wish")
public class WishlistController {
	
	//@Autowired
	private WishProductService wishproductService;
	public void setWishproductService(WishProductService wishproductService) {
		this.wishproductService = wishproductService;
	}
	
	@RequestMapping(value="/view", method = RequestMethod.GET)
	public String ProductWishList(Model model) throws Exception {
		
		return "user/my/wish/wishList"; 
	}


	
}

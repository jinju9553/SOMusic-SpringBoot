package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.WishProductService                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
import com.example.SOMusic.domain.WishGroupPurchase;


@Controller
@SessionAttributes("userSession")
@RequestMapping("/user")
public class WishlistController {
	
	//redirect 추후 수정
	private static final String WISH_SUCCESS = "/product/register/success";  
	//@Autowired
	private WishProductService wishproductService;
	public void setWishproductService(WishProductService wishproductService) {
		this.wishproductService = wishproductService;
	}
	
	@RequestMapping(value="/my/wish/view", method = RequestMethod.GET)
	public String ProductWishList(Model model) throws Exception {
		
		return "thyme/user/my/wish/wishList"; 
	}

	@PostMapping(value="/wish/add/{productId}")
	public String addWish(HttpServletRequest request,
			 Model model) throws Exception {
			int productId = Integer.parseInt(request.getParameter("productId"));
			
			WishProduct wish = new WishProduct();
			wish.setProductId(productId);
			wish.setUserId("panda");
			
			wishproductService.addWishproduct(wish);
			
			return "redirect:" + WISH_SUCCESS; //redirect
			}
			

	
}

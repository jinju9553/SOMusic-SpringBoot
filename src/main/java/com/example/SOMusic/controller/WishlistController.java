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
	
	
	//private static final String WISH_SUCCESS = "/product/register/success";  
	private static final String WISH_RE = "/product/info/";
	@Autowired
	private WishProductService wishproductService;
	public void setWishproductService(WishProductService wishproductService) {
		this.wishproductService = wishproductService;
	}
	
	@RequestMapping(value="/my/wish/view", method = RequestMethod.GET)
	public String ProductWishList(Model model) throws Exception {
		
		return "thyme/user/my/wish/wishList"; 
	}
	
	

	@GetMapping(value="/wish/add")
	public String addWish(
			@RequestParam("productId") int productId,
			 Model model) throws Exception {
			//int productId = Integer.parseInt(request.getParameter("productId"));
			
				WishProduct wish = new WishProduct();
				wish.setProductId(productId);
				wish.setUserId("panda");
				
				wishproductService.addWishproduct(wish);
				System.out.println("찜 추가 완료");
				
				return "redirect" + "/product/info?productId=" + productId; //redirect		
	
			}
			
	@GetMapping(value="/wish/delete")
	public String deleteWish(
		@RequestParam("productId") int productId) {
		String userId = "panda";
/*		
			WishProduct wish = new WishProduct();
			wish.setProductId(productId);
			wish.setUserId("panda");*/
			
			wishproductService.deleteWishproduct(userId, productId);
		
			return "redirect" + "/product/info?productId=" + productId; //redirect
	}
	
	
}

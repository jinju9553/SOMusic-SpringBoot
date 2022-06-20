package com.example.SOMusic.controller;

import java.util.List;

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
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.WishProductService                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.WishGroupPurchase;


@Controller
@SessionAttributes("userSession")
@RequestMapping("/user")
public class WishlistController {
	
	
	//private static final String WISH_SUCCESS = "/product/register/success";  
	private static final String WISH_RE = "/product/info/";
	private static final String WISH_PRODUCT_LIST ="thyme/user/my/wish/wishList";
	
	
	@Autowired
	private WishProductService wishproductService;
	public void setWishproductService(WishProductService wishproductService) {
		this.wishproductService = wishproductService;
	}
	
	/*
	 * @RequestMapping(value="/my/wish/view", method = RequestMethod.GET) public
	 * String ProductWishList(Model model) throws Exception {
	 * 
	 * return "thyme/user/my/wish/wishList"; }
	 */
	

	@GetMapping(value="/wish/add")
	public String addWish(HttpServletRequest request,
			@RequestParam("productId") int productId,
			 Model model) throws Exception {
			//int productId = Integer.parseInt(request.getParameter("productId"));
			
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
				WishProduct wish = new WishProduct();
				wish.setProductId(productId);
				wish.setUserId(userSession.getAccount().getUserId());
				
				wishproductService.addWishproduct(wish);
				System.out.println("찜 추가 완료");
				
				return WISH_PRODUCT_LIST; //여기서 바로 넘어가면 위시리스트 목록이 안 뜸 	
	
			}
	
	@GetMapping(value="/my/wish/view")
	public String WishList(HttpServletRequest request, Model model) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
		List<WishProduct> wishPrList = wishproductService.findWishProductList(userSession.getAccount().getUserId());
		model.addAttribute("wishPrList", wishPrList);
		
		return WISH_PRODUCT_LIST; 
	}
	
	
	
	@GetMapping(value="/wish/delete")
	public String deleteWish(HttpServletRequest request,
		@RequestParam("productId") int productId) {
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
/*		
			WishProduct wish = new WishProduct();
			wish.setProductId(productId);
			wish.setUserId("panda");*/
			
			wishproductService.deleteWishproduct(userSession.getAccount().getUserId(), productId);
		
			return WISH_PRODUCT_LIST; //여기서 바로 넘어가면 위시리스트 목록이 안 뜸 2
	}
	
	
}

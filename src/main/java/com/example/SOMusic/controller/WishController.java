package com.example.SOMusic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.WishGroupPurchase;
import com.example.SOMusic.domain.WishProduct;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.ProductService;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/my/wish")
public class WishController {
	
	//private static final String WISH_SUCCESS = "/product/register/success";  

	private static final String WISH_RE = "/product/info/";
	private static final String WISH_PRODUCT_LIST ="thyme/user/my/wish/myWishProductList";


	private static final String WISH_GP_LIST = "thyme/user/my/wish/myWishGPList";
	private static final String WISH_GP_LIST_URI = "/user/my/wish/gp/list"; // 위시 리스트에서 삭제 -> 위시 리스트로
	private static final String JOIN = "/join/";		// join 페이지로 uri 이동
	
	@Autowired
	private ProductService prSvc;
	public void setWishproductService(ProductService prSvc) {
		this.prSvc = prSvc;
	}
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
	// 상품 위시
	
	// 상품 위시 리스트
	@GetMapping(value="/product/list")
	public String WishList(HttpServletRequest request, Model model) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
		List<WishProduct> wishPrList = prSvc.findWishProductList(userSession.getAccount().getUserId());
		model.addAttribute("wishPrList", wishPrList);
		
		return WISH_PRODUCT_LIST; 
	}
	
	// 상품 위시 추가
	@GetMapping(value="/product/add")
	public String addWish(
			HttpServletRequest request,
			@RequestParam("productId") int productId,
			 Model model) throws Exception {

		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
			
		WishProduct wish = new WishProduct();
		wish.setProductId(productId);
		wish.setUserId(userSession.getAccount().getUserId());
				
		prSvc.addWishproduct(wish);
		System.out.println("찜 추가 완료");
				
		return "redirect:" + WISH_RE + "?productId=" + productId; //	
	}
			
	// 상품 위시 삭제
	@GetMapping(value="/product/delete")
	public String deleteWish(
			HttpServletRequest request,
			@RequestParam("productId") int productId) {
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
/*		
		WishProduct wish = new WishProduct();
		wish.setProductId(productId);
		wish.setUserId("panda");*/
			
		prSvc.deleteWishproduct(userSession.getAccount().getUserId(), productId);
		
		return  WISH_PRODUCT_LIST; //
	}

	// 공구 위시
	
	// 공구 위시 리스트 불러오기
	@RequestMapping(value="/gp/list", method = RequestMethod.GET)
	public String gpWishList(HttpServletRequest request, Model model) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
		List<WishGroupPurchase> wishGpList = gpSvc.getWishGPList(userSession.getAccount().getUserId());
		model.addAttribute("wishGpList", wishGpList);
		
		return WISH_GP_LIST;
	}
	
	// 공구 위시 등록하기
	@RequestMapping(value="/gp/add", method = RequestMethod.GET)
	public String wishGpRegister(HttpServletRequest request, @RequestParam("gpId") int gpId) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
		gpSvc.insertWishGP(userSession.getAccount().getUserId(), gpId);

		return "redirect:" + JOIN + gpId;
	}
	
	@RequestMapping(value="/gp/delete", method = RequestMethod.GET)
	public String wishGpDelete(HttpServletRequest request,
								@RequestParam("gpId") int gpId, @RequestParam("view") String view) throws Exception {

		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");

		gpSvc.deleteWishGP(userSession.getAccount().getUserId(), gpId);
		
		if (view.equals("join"))
			return "redirect:" + JOIN + gpId;		// join form 뷰에서 위시 취소
		else
			return "redirect:"+ WISH_GP_LIST_URI;		// 위시리스트에서 위시 취소
	}
	
}

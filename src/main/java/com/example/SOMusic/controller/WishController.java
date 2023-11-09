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

	private static final String WISH_RE = "/product/info/";
	private static final String WISH_PRODUCT_LIST ="thyme/user/my/wish/myWishProductList";
	private static final String WISH_PRODUCT_LIST_URI = "/user/my/wish/product/list";

	private static final String WISH_GP_LIST = "thyme/user/my/wish/myWishGPList";
	private static final String WISH_GP_LIST_URI = "/user/my/wish/gp/list";
	private static final String JOIN = "/join/";
	
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

	@GetMapping(value="/product/list")
	public String WishList(HttpServletRequest request, Model model) throws Exception {
	
		Login userSession = getUserSession(request);
		
		List<WishProduct> wishPrList = prSvc.findWishProductList(userSession.getAccount().getUserId());
		model.addAttribute("wishPrList", wishPrList);
		
		return WISH_PRODUCT_LIST; 
	}
	
	@GetMapping(value="/product/add")
	public String addWish(HttpServletRequest request, @RequestParam("productId") int productId, Model model) throws Exception {

		Login userSession = getUserSession(request);
			
		WishProduct wish = new WishProduct();
		wish.setProductId(productId);
		wish.setUserId(userSession.getAccount().getUserId());
				
		prSvc.addWishproduct(wish);
		System.out.println("찜 추가 완료");
				
		return "redirect:" + WISH_RE + "?productId=" + productId;	
	}
			
	@GetMapping(value="/product/delete")
	public String deleteWish(HttpServletRequest request, @RequestParam(value="view", required=false) String view,
							@RequestParam("productId") int productId) {
		
		Login userSession = getUserSession(request);
	
		prSvc.deleteWishproduct(userSession.getAccount().getUserId(), productId);
		
		if(view.equals("list")) {
			System.out.println(view + "에서 삭제했습니다.");
			return "redirect:" + WISH_PRODUCT_LIST_URI;
		}

		if(view.equals("view"))
			System.out.println("뷰에서 삭제했습니다.");
			return  "redirect:" + WISH_RE + "?productId=" + productId; 
	}

	@RequestMapping(value="/gp/list", method = RequestMethod.GET)
	public String wishGpList(HttpServletRequest request, Model model) throws Exception {
		
		Login userSession = getUserSession(request);

		List<WishGroupPurchase> wishGpList = gpSvc.getWishGPList(userSession.getAccount().getUserId());
		model.addAttribute("wishGpList", wishGpList);
		
		return WISH_GP_LIST;
	}

	@RequestMapping(value="/gp/add", method = RequestMethod.GET)
	public String wishGpRegister(HttpServletRequest request, @RequestParam("gpId") int gpId) throws Exception {
		
		Login userSession = getUserSession(request);
    
		gpSvc.insertWishGP(userSession.getAccount().getUserId(), gpId);

		return "redirect:" + JOIN + gpId;
	}
	
	@RequestMapping(value="/gp/delete", method = RequestMethod.GET)
	public String wishGpDelete(HttpServletRequest request,
								@RequestParam("gpId") int gpId, @RequestParam("view") String view) throws Exception {
		Login userSession = getUserSession(request);

		gpSvc.deleteWishGP(userSession.getAccount().getUserId(), gpId);
		
		if (isFromJoinView(view))
			return "redirect:" + JOIN + gpId;
		else
			return "redirect:"+ WISH_GP_LIST_URI;
	}
	
	private Login getUserSession(HttpServletRequest request) {
		return (Login) WebUtils.getSessionAttribute(request, "userSession");
	}
	
	private boolean isFromJoinView(String view) {
		return view.equals("join");
	}
  
}

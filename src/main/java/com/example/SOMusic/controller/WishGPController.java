package com.example.SOMusic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.WishGroupPurchase;
import com.example.SOMusic.service.GPService;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/my/wish")
public class WishGPController {
	
	private static final String WISH_GP_LIST = "thyme/user/my/gp/MyWishGPList";
	private static final String WISH_GP_LIST_URI = "/user/my/wish/gp/list"; // 위시 리스트에서 삭제 -> 위시 리스트로
	private static final String JOIN = "/join/";		// join 페이지로 uri 이동
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}

	@RequestMapping(value="/gp/list", method = RequestMethod.GET)
	public String gpWishList(HttpServletRequest request, Model model) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
		List<WishGroupPurchase> wishGpList = gpSvc.getWishGPList(userSession.getAccount().getUserId());
		model.addAttribute("wishGpList", wishGpList);
		
		return WISH_GP_LIST;
	}
	
	@RequestMapping(value="/gp", method = RequestMethod.GET)
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

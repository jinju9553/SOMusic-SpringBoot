package com.example.SOMusic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.domain.WishGroupPurchase;
import com.example.SOMusic.service.GPService;

@Controller
@RequestMapping("/user/my/wish")
public class WishGPController {
	
	private static final String WISH_GP_LIST = "thyme/user/my/gp/MyWishGPList";
	private static final String JOIN = "/join/";		// join 페이지로 uri 이동
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}

	@RequestMapping(value="/gp/list", method = RequestMethod.GET)
	public String gpWishList(Model model) throws Exception {
		String userId = "somsom2";	// 세션에서 가져오기, 지금 임의 설정
		
		List<WishGroupPurchase> wishGpList = gpSvc.getWishGPList(userId);
		
//		System.out.println("위시 공구 리스트 : " + wishGpList);
		
		model.addAttribute("wishGpList", wishGpList);
		
		return WISH_GP_LIST;
	}
	
	@RequestMapping(value="/gp", method = RequestMethod.GET)
	public String wishGpRegister(@RequestParam("gpId") int gpId) throws Exception {
		
		String userId = "somsom2";	// 세션에서 가져오기, 임시로 somsom2 설정
		
//		System.out.println("위시 공구 : " + gpId);
		
		gpSvc.insertWishGP(userId, gpId);

		return "redirect:" + JOIN + gpId;
	}
	
	@RequestMapping(value="/gp/delete", method = RequestMethod.GET)
	public String wishGpDelete(@RequestParam("gpId") int gpId, @RequestParam("view") String view) throws Exception {
		String userId = "somsom2";	// 세션에서 가져오기, 임시로 somsom2 설정
		
		System.out.println("위시 삭제 : " + userId + " , " + gpId);
		
		gpSvc.deleteWishGP(userId, gpId);
		
		if (view.equals("join"))
			return "redirect:" + JOIN + gpId;		// join form 뷰에서 위시 취소
		else
			return WISH_GP_LIST;		// 위시리스트에서 위시 취소
	}
}

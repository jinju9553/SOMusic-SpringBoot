package com.example.SOMusic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;

@Controller
@RequestMapping("/user/my/gp")
public class MyGPController {
	
	private static final String MY_GP_INFO = "thyme/user/my/gp/myGPInfo";
	private static final String MY_REGISTER_LIST = "thyme/user/my/gp/MyGPList";
	private static final String MY_JOIN_LIST = "thyme/user/my/join/MyJoinList";

	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
	@Autowired
	private JoinService joinService;
	public void setJoinService(JoinService joinService) {
		this.joinService = joinService;
	}
	
//	// 공구 찾아서 전달
//	@ModelAttribute("gpReq")
//	public GPRequest show() throws Exception {
//		return new GPRequest();
//	}

	@RequestMapping(value="/info", method = RequestMethod.GET)
	public String info(@RequestParam("gpId") int gpId, Model model) throws Exception {
		System.out.println("GP 정보 : " + gpId);

		// 공구 검색
		GroupPurchase gp = gpSvc.getGP(gpId);
		model.addAttribute("gp", gp);
		
		return MY_GP_INFO;
	}
	
	@RequestMapping(value="/register/list", method = RequestMethod.GET)
	public String registerList(@RequestParam("sellerId") String sellerId, Model model) throws Exception {
		
		List<GroupPurchase> gpList = gpSvc.getMyGPList(sellerId);
		model.addAttribute("gpList", gpList);
		
		System.out.println("등록한 공구 리스트 : " + gpList.toString());
		
		return MY_REGISTER_LIST;
	}
	
	@RequestMapping(value="/join/list", method = RequestMethod.GET)
	public String joinList(@RequestParam("userId") String userId, Model model) throws Exception {
		
		List<Join> joinList = joinService.findAllByUserId(userId);
		model.addAttribute("joinList", joinList);
		
		System.out.println("내가 참여한 공구 리스트 : " + joinList.toString());

		return MY_JOIN_LIST;
	}
}

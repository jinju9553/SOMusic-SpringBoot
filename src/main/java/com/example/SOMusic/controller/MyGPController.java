package com.example.SOMusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.service.GPService;

@Controller
@RequestMapping("/user/my/gp")
public class MyGPController {
	
	private static final String MY_GP_INFO = "user/my/gp/myGPInfo";
	private static final String MY_REGISTER_LIST = "user/my/gp/MyGPList";
	private static final String MY_JOIN_LIST = "user/my/gp/MyJoinList";

	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
//	// 공구 찾아서 전달
//	@ModelAttribute("gpReq")
//	public GPRequest show() throws Exception {
//		return new GPRequest();
//	}

	@RequestMapping(value="/info", method = RequestMethod.GET)
	public String info(Model model) throws Exception {
		System.out.println("GP 정보 : ");

		// 공구 검색
//		GPRequest gp = gpSvc.gpfind(gpReq);
		
//		model.addAttribute("grupPurchase", gp);
		
		return MY_GP_INFO;
	}
	
	@RequestMapping(value="/register/list", method = RequestMethod.GET)
	public String registerList(Model model) throws Exception {

		
		return MY_REGISTER_LIST;
	}
	
	@RequestMapping(value="/join/list", method = RequestMethod.GET)
	public String joinList(Model model) throws Exception {


		return MY_JOIN_LIST;
	}
}

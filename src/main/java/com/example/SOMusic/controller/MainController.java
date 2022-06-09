package com.example.SOMusic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.service.GPService;

@Controller
public class MainController {
	
	private static final String MAIN = "/main";		// '/'가 들어오면 '/main' 실행
	private static final String MAIN_VIEW = "thyme/main";
	private static final String MAIN_GP_VIEW = "thyme/gp/list/GPListView";
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
//	@RequestMapping("/")
//	public String goMain() {
//		return "redirect:" + MAIN;
//	}
	
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String showMainList(Model model) {
		
		List<GroupPurchase> gpList = gpSvc.get4GPList();
		
		System.out.println("메인 공구 리스트 : " + gpList);
		
		model.addAttribute("gpList", gpList);
		
		return MAIN_VIEW;
	}
	
	@RequestMapping(value="/main/gp", method = RequestMethod.GET)
	public String showGPList(Model model) {
		
		List<GroupPurchase> gpList = gpSvc.getAllGPList();
		
		System.out.println("공구 리스트 : " + gpList);
		
		model.addAttribute("gpList", gpList);
		
		return MAIN_GP_VIEW;
	}

}

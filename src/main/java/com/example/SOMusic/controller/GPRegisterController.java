package com.example.SOMusic.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.SOMusic.service.GPService;

@Controller
@RequestMapping("/gp")
public class GPRegisterController {
	
	private static final String GP_REGISTER_FORM = "gp/register/GPRegisterForm";
	private static final String GP_REGISTER_SEUCCESS = "/gp/register/success";	// redirect : uri
	private static final String GP_REGISTER_SEUCCESS_View = "gp/register/GPRegisterSuccess";
	
	private static final String GP_UPDATE_FORM = "gp/update/GPUpdateForm";
	private static final String GP_UPDATE_SEUCCESS = "/user/my/gp/info";	// redirect : uri
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
	@ModelAttribute("gpReq")
	public GPRequest formBacking() throws Exception {
		return new GPRequest();
	}
	
	// 공구 등록
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String showRegisterForm() {
		return GP_REGISTER_FORM;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(
			@Valid @ModelAttribute("gpReq") GPRequest gpReq, Errors errors,
			Model model) throws Exception {
		System.out.println("GP 등록 : " + gpReq);
		
		// 오류
		if(errors.hasErrors()) {
			return GP_REGISTER_FORM;
		}

		// 공구 등록
//		GPRequest gp = gpSvc.gpRegister(gpReq);
		
		model.addAttribute("grupPurchase", gpReq);
		
		return "redirect:" + GP_REGISTER_SEUCCESS;	// redirect로 넘기기
	}
	
	@RequestMapping(value="/register/success", method = RequestMethod.GET)
	public String success(Model model) throws Exception {
		System.out.println("GP 등록 완료");
		
		return GP_REGISTER_SEUCCESS_View;
	}

	
	// 등록한 공구 정보 수정
	
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String showUpdateForm() {
		return GP_UPDATE_FORM;
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(
			@ModelAttribute("gpReq") GPRequest gpReq, Errors errors,
			Model model) throws Exception {
		System.out.println("GP 수정 : " + gpReq);
		
		// 오류
		if(errors.hasErrors()) {
			return GP_UPDATE_FORM;
		}

		// 공구 수정
//		GPRequest gp = gpSvc.gpRegister(gpReq);
		
		return "redirect:" + GP_UPDATE_SEUCCESS;
	}
	
	
	
	
//	public String delete() {
//		
//	}
	
}

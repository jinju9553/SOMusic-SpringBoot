package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.SOMusic.domain.Join;
import com.example.SOMusic.service.JoinService;
import com.example.SOMusic.service.JoinValidator;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/join")
public class MyJoinController {
	private static final String JOIN_INFO = "join/myJoinInfo";
	
	@Autowired
	private JoinService joinService;
	public void setJoinService(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@ModelAttribute("shippingOption")
	public Object[] referenceData() { //주의: String이나 Object만 전송 가능
		return new Object[] { 1, 2, 3 }; 
	}
	
	@ModelAttribute("shippingText")
	public Object[] referenceString() { 
		return new Object[] { "준등기 (+1,800원)", "택배 (+3,000원)", "택배(제주산간) (+6,000원)" };
	}
	
	@ModelAttribute("shippingCost")
	public Object[] referenceCost() { 
		return new Object[] { 1800, 3000, 6000 };
	}
	
	@Autowired
	private JoinValidator validator;
	public void setValidator(JoinValidator validator) {
		this.validator = validator;
	}
	
	@GetMapping("/info/{joinId}")
	public String showForm() {
		return JOIN_INFO;
	}
	
	@ModelAttribute("joinInfoReq")
	public Join formBackingInfo(HttpServletRequest request,
				@PathVariable("joinId") int joinId) {
		if (request.getMethod().equalsIgnoreCase("GET")) { 
			Join join = joinService.findJoinByJoinId(joinId); //DB에서 특정 join을 읽어온다.

			return join;
		}
		else return new Join();
	}
	
	@PostMapping("/info/{joinId}")
	public String update(
			@ModelAttribute("joinInfoReq") Join join,
			BindingResult result, Model model) throws Exception { 

		validator.validate(join, result);

		if (result.hasErrors()) {
			for ( ObjectError r :  result.getAllErrors()) {
				System.out.println(r);
			}
			model.addAttribute("joinInfoReq", join);
			return JOIN_INFO;
		}
		
		joinService.modifyJoin(join);

		return "redirect:/" + "join/info/{joinId}"; //본래의 경로로 redirection
	}
	
	@GetMapping("/delete/{joinId}")
	public String delete(@PathVariable("joinId") int joinId) throws Exception {

		Join join = joinService.findJoinByJoinId(joinId);
		join.setGroupPurchase(null); //연관된 객체 해제
		
		joinService.deleteJoin(joinId);
		
		return "redirect:/" + "user/my/join/list";
	}
}

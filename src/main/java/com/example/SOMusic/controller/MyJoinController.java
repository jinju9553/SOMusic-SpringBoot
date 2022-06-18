package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.SOMusic.domain.Join;
import com.example.SOMusic.service.JoinService;

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
	public Object[] referenceData() { //String이나 Object만 전송 가능
		return new Object[] { 1, 2, 3 }; //"준등기 (+1,800원)", "택배 (+3,000원)", "택배(제주산간) (+6,000원)"
	}
	
	@ModelAttribute("shippingCost")
	public Object[] referenceCost() { 
		return new Object[] { 1800, 3000, 6000 };
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
			
			System.out.println("INFO - GET: " + join.getGroupPurchase().getTitle());
			
			return join;
		}
		else return new Join();
	}
	
	@PostMapping("/info/{joinId}")
	public String update(
			@ModelAttribute("joinInfoReq") Join join,
			BindingResult result) throws Exception { //다시 받아오면 GP가 날라감 ==> Update에도 영향을 줘서 gpId가 날라감

		//if (result.hasErrors()) {
		//	return JOIN_INFO;
		//}

		int newShippingCost = joinService.initShippingCost(join);
		join.setTotalAmount(joinService.updateTotal(join, newShippingCost));
		join.setShippingCost(newShippingCost);
		joinService.modifyJoin(join);

		return "redirect:/" + "join/info/{joinId}"; //본래의 경로로 redirection
	}
}

package com.example.SOMusic.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.WishGroupPurchase;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/join")
public class JoinController {
	private static final String JOIN_FORM = "join/joinForm";
	
	@Autowired
	private JoinService joinService;
	public void setJoinService(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@Autowired
	private GPService gpService;
	public void setGPService(GPService gpService) {
		this.gpService = gpService;
	}
	
	@Autowired
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@ModelAttribute("shippingOption")
	public Object[] referenceData() { //String이나 Object만 전송 가능
		return new Object[] { 1, 2, 3 }; //"준등기 (+1,800원)", "택배 (+3,000원)", "택배(제주산간) (+6,000원)"
	}
	
	@ModelAttribute("shippingCost")
	public Object[] referenceCost() { 
		return new Object[] { 1800, 3000, 6000 };
	}
	
	//1.Validator

	//2.showForm()
	@GetMapping("/{gpId}")
	public String showForm(HttpServletRequest request,
							@PathVariable("gpId") int gpId, Model model) {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		WishGroupPurchase wishGp = null;
		
		if(userSession != null) {
			// wish에 대한 정보
			wishGp = gpService.getWishGP(userSession.getAccount().getUserId(), gpId);
		}
		
		model.addAttribute("wishGp", wishGp);
		
		return JOIN_FORM;
	}
	
	@ModelAttribute("joinReq") // request handler methods 보다 먼저 호출됨
	public Join formBacking(HttpServletRequest request,
			@PathVariable("gpId") int gpId, Model model) { 
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Join join = new Join();
	
			GroupPurchase gp = gpService.getGP(gpId);
	
			// 1.UserSession에서 UserId를 뽑아낸다.
			Login userSession = 
					(Login) WebUtils.getSessionAttribute(request, "userSession");
			
			// 2.Account를 통해 이 유저의 address 및 기본 정보를 읽어와서 세팅한다.
			Account account = accountService.getAccount(userSession.getAccount().getUserId());
			
			model.addAttribute("account", account); //정보를 세팅하여 Form에 초기값으로 나타낸다.
	
			join.setGroupPurchase(gp); // 정보를 세팅하여 Form에 초기값으로 나타낸다.
			if(join.getQuantity() == 0) {//최초로 폼을 불러왔다면
				join.setQuantity(1); //개수는 1개부터 시작
				join.setShippingMethod(1); //디폴트 값
			}
	
			return join;
		}
		else return new Join(); //POST일 때 실행됨
	}
	
	@PostMapping("/{gpId}")
	public String register( 
			HttpServletRequest request,
			@PathVariable("gpId") int gpId,
			@ModelAttribute("joinReq") Join join,
			BindingResult result, Model model) {
		
		Login userSession = 
				(Login) WebUtils.getSessionAttribute(request, "userSession");
		
		if (result.hasErrors()) {
			WishGroupPurchase wishGp = gpService.getWishGP(userSession.getAccount().getUserId(), gpId);		
			model.addAttribute("wishGp", wishGp);
			
			return JOIN_FORM;
		}
		
		join.setConsumerId(userSession.getAccount().getUserId());
		join.setGroupPurchase(gpService.getGP(gpId));
		join.setShippingCost(joinService.initShippingCost(join));
		join.setTotalAmount(joinService.calculateTotal(join.getGroupPurchase(), join));
		join.setStatus(1); //참여하자마자는 1
		join.setRegDate(new Date()); //DB에서 SYSDATE가 자동으로 찍히지 않음
		
		joinService.registerJoin(join);

		return "redirect:/" + "join/{gpId}";
	}
}

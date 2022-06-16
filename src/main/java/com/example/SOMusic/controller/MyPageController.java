package com.example.SOMusic.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.PurchaseService;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/my")
public class MyPageController {
	
	private static final String MY_PAGE = "thyme/user/my/myPage";
	private static final String MY_PURCHASE_LIST = "thyme/user/my/purchase/MyPurchaseList";
	private static final String RECOVER_ID_PAGE = "thyme/user/account/recoverAccount";
	private static final String RESET_PW_PAGE = "thyme/user/account/resetPassword";
	
	@Autowired
	private PurchaseService purchaseService;
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@Autowired
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping
	public String showForm() {
		return MY_PAGE;
	}
	
	//user 정보를 포함하는 formBacking
	@ModelAttribute("accountForm")
	public AccountForm formBacking(HttpServletRequest request) { 
		if (request.getMethod().equalsIgnoreCase("GET")) {
			
			// 1.UserSession에서 UserId를 가져온다.
			Login userSession = 
					(Login) WebUtils.getSessionAttribute(request, "userSession");
			
			// 2.세션에서 얻은 Id로 사용자 정보를 가져온다.
			AccountForm accountForm = new AccountForm(accountService.getAccount(userSession.getAccount().getUserId()));

			return accountForm;
		}
		else return new AccountForm(); //POST일 때 실행됨
	}
	
	@PostMapping("/update")
	public String update( 
			HttpServletRequest request,
			@ModelAttribute("accountForm") AccountForm accountForm,
			BindingResult bindingResult) throws ModelAndViewDefiningException {
		
		if (bindingResult.hasErrors()) {
			return MY_PAGE;
		}

		Login userSession = 
				(Login) WebUtils.getSessionAttribute(request, "userSession");
		accountForm.getAccount().setPassword(userSession.getAccount().getPassword()); //비밀번호 수정은 별도로 구현
		accountService.updateAccount(accountForm.getAccount());

		return "redirect:/" + "user/my"; //GET 요청 발생
	}
	
	@PostMapping("/update/password")
	public String updatePassword( 
			HttpServletRequest request,
			@ModelAttribute("newAccountForm") AccountForm newAccountForm,
			BindingResult bindingResult) throws ModelAndViewDefiningException {
	
		//미구현
		//accountService.updatePassword(newAccountForm.getAccount());

		return "redirect:/" + "user/my"; //새 화면 만들기
	}
	
	@GetMapping("/drop")
	public String drop(HttpServletRequest request) throws ModelAndViewDefiningException {

		Login userSession = 
				(Login) WebUtils.getSessionAttribute(request, "userSession");
		accountService.deleteAccount(userSession.getAccount());

		return "redirect:/" + "user/logout"; //세션에서 삭제
	}
	
	@PostMapping("/find/id")
	public ModelAndView findId( 
			HttpServletRequest request,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email, Model model) throws ModelAndViewDefiningException {

		Account account = accountService.findAccount(phone, email);
		
		if(account != null)
			model.addAttribute("id", account.getUserId());

		return new ModelAndView(RECOVER_ID_PAGE); 
	}
	
	@PostMapping("/find/password")
	public ModelAndView findPassword( 
			HttpServletRequest request,
			@RequestParam("userId") String userId,
			@RequestParam("phone") String phone, Model model) throws ModelAndViewDefiningException {

		Account account = accountService.findPassword(userId, phone);
		
		if(account != null) //문제: 마이페이지에서 넘어올 때도 newAccountForm을 인식해야 함
			model.addAttribute("newAccountForm", new AccountForm(account));

		return new ModelAndView(RESET_PW_PAGE);
	}
	
	@GetMapping("/purchase/list")
	public String registerList(HttpServletRequest request, Model model) throws Exception {
		
		Login userSession = 
				(Login) WebUtils.getSessionAttribute(request, "userSession");
					
		List<Purchase> pList = purchaseService.findPurchaseList(userSession.getAccount().getUserId());
		model.addAttribute("pList", pList);
		
		System.out.println("구매한 상품 리스트 : " + pList.toString());
		
		return MY_PURCHASE_LIST;
	}
}

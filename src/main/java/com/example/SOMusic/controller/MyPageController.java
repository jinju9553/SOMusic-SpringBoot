package com.example.SOMusic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.AccountFormValidator;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.PurchaseService;

@Controller
@SessionAttributes({"userSession", "tempSession"})
@RequestMapping("/user/my")
public class MyPageController {
	
	private static final String MY_PAGE = "thyme/user/my/myPage";
	private static final String RECOVER_ID_PAGE = "thyme/user/account/recoverAccount";
	private static final String RESET_PW_PAGE = "thyme/user/account/resetPassword";

	@Autowired
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Autowired
	private AccountFormValidator validator;
	public void setValidator(AccountFormValidator validator) {
		this.validator = validator;
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
			BindingResult result) throws ModelAndViewDefiningException {
		
		accountForm.setNewAccount(false);
		validator.validate(accountForm, result);
		
		if (result.hasErrors()) {
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
			@RequestParam("password") String password) throws ModelAndViewDefiningException {
	
		Login userSession = 
				(Login) WebUtils.getSessionAttribute(request, "userSession");
		
		if (userSession == null) { //로그인된 상태가 아니라면 temp를 꺼낸다.
			userSession = (Login) WebUtils.getSessionAttribute(request, "tempSession");
			request.removeAttribute("tempSession");
		}
		
		accountService.updatePassword(userSession.getAccount(), password);
	
		return "redirect:/" + "user/loginForm"; //변경 후에 로그인 폼으로
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
	
	@RequestMapping("/find/password")
	public ModelAndView findPassword( 
			HttpServletRequest request,
			@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="phone", required=false) String phone, Model model) throws ModelAndViewDefiningException {
		if (request.getMethod().equalsIgnoreCase("POST")) {
			//1.비밀번호 찾기 메뉴에서 넘어온 경우
			Account account = accountService.findPassword(userId, phone);
			
			if(account != null) { //계정이 검색되었다면 세션 생성
				Login tempSession = new Login(account);
				model.addAttribute("tempSession", tempSession); //로그인된 상태와 구분하기 위해 명칭 변경
				model.addAttribute("searchResult", true);
			}
			else
				model.addAttribute("searchResult", false);
		
			return new ModelAndView(RESET_PW_PAGE);
		}
		else { //GET mapping이라면 
			//2.마이페이지에서 넘어온 경우 ==> 세션 존재
			Login userSession = 
					(Login) WebUtils.getSessionAttribute(request, "userSession");
			
			if (userSession == null) //세션 만료시
				return new ModelAndView("redirect:/" + "user/logout");
				
			model.addAttribute("searchResult", true);
			
			return new ModelAndView(RESET_PW_PAGE);
		}
	}
}

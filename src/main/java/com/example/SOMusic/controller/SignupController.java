package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountFormValidator;
import com.example.SOMusic.service.AccountService;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/register")
public class SignupController {
	
	private static final String REGISTER_FORM = "thyme/user/account/registerForm";
	
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
	
	@ModelAttribute("accountForm")
	public AccountForm formBacking(HttpServletRequest request) throws Exception {
		Login userSession = 
			(Login) WebUtils.getSessionAttribute(request, "userSession");
		String uri = request.getRequestURI();
		if (userSession != null && !(uri.contains("/checkId"))) { // 세션이 있다면 회원 수정
			return new AccountForm(
					accountService.getAccount(userSession.getAccount().getUserId()));
		}
		else { // 세션이 없다면 새로 회원가입
			return new AccountForm();
		}
	}
	
	@GetMapping
	public String showForm() {
		return REGISTER_FORM;
	}
	
	@PostMapping
	public String register(
			HttpServletRequest request, HttpSession session,
			@ModelAttribute("accountForm") AccountForm accountForm,
			BindingResult result) throws Exception {
		
		validator.validate(accountForm, result);
		
		if (result.hasErrors()) return REGISTER_FORM;
		try {
			if (accountForm.isNewAccount()) {
				accountService.insertAccount(accountForm.getAccount());
			}
		}
		catch (DataIntegrityViolationException ex) {
			result.rejectValue("account.username", "USER_ID_ALREADY_EXISTS",
					"*중복된 아이디입니다."); // (/checkId)에서도 점검
			return REGISTER_FORM; 
		}
		
		Login userSession = new Login(
				accountService.getAccount(accountForm.getAccount().getUserId()));

		session.setAttribute("userSession", userSession);
		return "redirect:/" + "main"; //홈 화면으로 리다이렉션
	}
	
	@ResponseBody
	@PostMapping("/checkId")
	public boolean check(@RequestParam("id") String userId) {
		//아이디 중복 확인
		return accountService.isDuplicated(userId);
	}
}

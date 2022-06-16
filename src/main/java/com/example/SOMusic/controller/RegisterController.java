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
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountService;

@Controller
@RequestMapping("/user/register")
public class RegisterController {
	
	private static final String REGISTER_FORM = "thyme/user/account/registerForm";
	
	@Autowired
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@ModelAttribute("accountForm")
	public AccountForm formBacking(HttpServletRequest request) throws Exception {
		Login userSession = 
			(Login) WebUtils.getSessionAttribute(request, "userSession");
		if (userSession != null) {	// 세션이 있다면 회원 수정으로 넘어감
			return new AccountForm(
					accountService.getAccount(userSession.getAccount().getUserId()));
		}
		else {	// 세션이 없다면 새로 회원가입
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
		
		//validator.validate(accountForm, result);
		
		if (result.hasErrors()) return REGISTER_FORM;
		try {
			if (accountForm.isNewAccount()) {
				accountService.insertAccount(accountForm.getAccount());
			}
			else { //사용하지 않음
				accountService.updateAccount(accountForm.getAccount());
			}
		}
		catch (DataIntegrityViolationException ex) {
			result.rejectValue("account.username", "USER_ID_ALREADY_EXISTS",
					"User ID already exists: choose a different ID.");
			return REGISTER_FORM; 
		}
		
		Login userSession = new Login(
				accountService.getAccount(accountForm.getAccount().getUserId()));

		session.setAttribute("userSession", userSession);
		return "redirect:/" + "main"; //홈 화면으로 리다이렉션
	}
}

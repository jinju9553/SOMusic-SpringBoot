package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Login;

@Controller
@RequestMapping({"/user/register", "/user/update"})
public class RegisterController {
	
	private static final String REGISTER_FORM = "";
	
	@ModelAttribute("accountForm")
	public AccountForm formBackingObject(HttpServletRequest request) throws Exception {
		Login userSession = 
			(Login) WebUtils.getSessionAttribute(request, "userSession");
		if (userSession != null) {	// 회원 수정으로 넘어감
			return new AccountForm(
				petStore.getAccount(userSession.getAccount().getUsername()));
		}
		else {	// 새로 회원가입
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

		accountForm.getAccount().getProfile().setUserid(
			accountForm.getAccount().getUsername());

		if (request.getParameter("account.profile.listOption") == null) {
			accountForm.getAccount().getProfile().setListOption(false);
		}
		if (request.getParameter("account.profile.bannerOption") == null) {
			accountForm.getAccount().getProfile().setBannerOption(false);
		}
		
		//validator.validate(accountForm, result);
		
		if (result.hasErrors()) return REGISTER_FORM;
		try {
			if (accountForm.isNewAccount()) {
				petStore.insertAccount(accountForm.getAccount());
			}
			else {
				petStore.updateAccount(accountForm.getAccount());
			}
		}
		catch (DataIntegrityViolationException ex) {
			result.rejectValue("account.username", "USER_ID_ALREADY_EXISTS",
					"User ID already exists: choose a different ID.");
			return REGISTER_FORM; 
		}
		
		Login userSession = new Login(
			petStore.getAccount(accountForm.getAccount().getUsername()));
		PagedListHolder<Product> myList = new PagedListHolder<Product>(
			petStore.getProductListByCategory(
					accountForm.getAccount().getProfile().getFavouriteCategoryId()));
		myList.setPageSize(4);
		userSession.setMyList(myList);
		session.setAttribute("userSession", userSession);
		return "redirect:/" + "main";  
	}
}

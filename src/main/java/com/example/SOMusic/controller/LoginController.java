package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountService;

@Controller
@SessionAttributes("userSession")
public class LoginController {

	@Autowired
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping("/user/login")
	public ModelAndView login(HttpServletRequest request,
			@RequestParam("userId") String userId,
			@RequestParam("password") String password,
			@RequestParam(value="forwardAction", required=false) String forwardAction,
			Model model) throws Exception {
		Account account = accountService.getAccount(userId, password); //DB의 아이디 & 비밀번호와 대조
		if (account == null) {
			return new ModelAndView("Error", "message", 
					"Invalid username or password.  Signon failed.");
		}
		else {
			Login userSession = new Login(account);

			model.addAttribute("userSession", userSession);
			
			if (forwardAction != null) {
				System.out.println("LoginController: " + forwardAction);
				return new ModelAndView("redirect:" + forwardAction); //사용자가 원래 보던 페이지로 리다이렉트
			}
			else 
				return new ModelAndView("redirect:" + "/main"); //그냥 redirect하면 request가 소멸하므로 MainController에도 userSession 연동
		}
	}
}

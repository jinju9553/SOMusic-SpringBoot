package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("userSession")
public class LoginController {

	@Autowired
	private AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@RequestMapping("/user/login")
	public ModelAndView login(
			@RequestParam("userId") String userId,
			@RequestParam("password") String password,
			@RequestParam(value = "forwardAction", required = false) String forwardAction,
			Model model) throws Exception {

		Account account = accountService.getAccount(userId, password);

		if (account == null) {
			return new ModelAndView("Error", "message",
					"아이디 또는 패스워드가 잘못 되었습니다.  다시 시도해주세요.");
		} else {
			Login userSession = new Login(account);

			model.addAttribute("userSession", userSession);

			if (forwardAction != null) {
				System.out.println("LoginController: " + forwardAction);
				return new ModelAndView("redirect:" + forwardAction);
			} else {
				return new ModelAndView("redirect:" + "/main");
			}
		}
	}
}

package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Login;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	private static final String LOGIN_FORM = "thyme/user/account/loginForm";

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler)
			throws Exception {

		Login userSession =
				(Login) WebUtils.getSessionAttribute(request, "userSession");

		if (userSession == null) {
			String url = request.getRequestURL().toString();
			String query = request.getQueryString();

			ModelAndView modelAndView = new ModelAndView(LOGIN_FORM);

			System.out.println("Interceptor: preHandle");
			if (query != null) {
				modelAndView.addObject("forwardAction", url + "?" + query);
			} else {
				modelAndView.addObject("forwardAction", url);
			}

			throw new ModelAndViewDefiningException(modelAndView);
		} else {
			return true;
		}
	}
}

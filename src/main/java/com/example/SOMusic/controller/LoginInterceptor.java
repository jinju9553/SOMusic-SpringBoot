package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Login;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	private static final String LOGIN_FORM = "thyme/user/account/loginForm";
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		Login userSession = 
			(Login) WebUtils.getSessionAttribute(request, "userSession"); //request로부터 userSession을 가져온다.
		if (userSession == null) { //로그인 된 기록이 없다면
			String url = request.getRequestURL().toString(); //사용자의 현재 url을 보존하고 로그인 폼으로 이동
			String query = request.getQueryString();
			ModelAndView modelAndView = new ModelAndView(LOGIN_FORM);
			if (query != null) { //만약 url에 query string이 존재한다면
				modelAndView.addObject("signonForwardAction", url+"?"+query); //따로 설정해준다.
			}
			else {
				modelAndView.addObject("signonForwardAction", url);
			}
			throw new ModelAndViewDefiningException(modelAndView);
		}
		else {
			return true;
		}
	}
}

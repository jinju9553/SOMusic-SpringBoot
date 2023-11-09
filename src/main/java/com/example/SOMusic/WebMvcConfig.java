package com.example.SOMusic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private static final String LOGIN_FORM = "thyme/user/account/loginForm";
	private static final String FIND_ID_FORM = "thyme/user/account/findAccountForm";
	private static final String FIND_PW_FORM = "thyme/user/account/findPasswordForm";

	@Autowired
	@Qualifier(value = "loginInterceptor")
	private HandlerInterceptor interceptor;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/user/loginForm").setViewName(LOGIN_FORM);
		registry.addViewController("/user/findIdForm").setViewName(FIND_ID_FORM);
		registry.addViewController("/user/findPwForm").setViewName(FIND_PW_FORM);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.addPathPatterns("/gp/register", "/product/register");
	}
}

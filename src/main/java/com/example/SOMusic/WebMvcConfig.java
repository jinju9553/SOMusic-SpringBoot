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
	@Qualifier(value = "loginInterceptor") //미리 만들어져 있는 Intercepter를 주입한다.
	private HandlerInterceptor interceptor;

	//별도의 handler 처리 없이 url에 따라 바로 보여줄 view를 설정한다. (ForwardController)
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/user/loginForm").setViewName(LOGIN_FORM);
		registry.addViewController("/user/findIdForm").setViewName(FIND_ID_FORM);
		registry.addViewController("/user/findPwForm").setViewName(FIND_PW_FORM);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor) //아래의 url로 접속하면 loginForm으로 즉시 이동
				.addPathPatterns("/gp/register", "/product/register");		
	}
}

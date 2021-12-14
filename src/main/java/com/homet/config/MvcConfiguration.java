package com.homet.config;

import java.util.Arrays; 
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.homet.interceptor.LoginInterceptor;



@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> patterns= Arrays.asList("/detail","/write","/user/mypage","/health/*","/mealkit/*");
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns(patterns);
	}
}
package com.homet.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private String resourcePath = "file:///C:/freeboard/img/";  //실제 경로
	
	private String uploadPath="/img/**";   //가져올 때 작성할 경로
	
	private String mealkitPath="/kit/**";   //가져올 때 작성할 경로
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler(uploadPath)
			.addResourceLocations(resourcePath);
			
			registry.addResourceHandler(mealkitPath)
			.addResourceLocations("classpath:/static/kit/");
		}
}
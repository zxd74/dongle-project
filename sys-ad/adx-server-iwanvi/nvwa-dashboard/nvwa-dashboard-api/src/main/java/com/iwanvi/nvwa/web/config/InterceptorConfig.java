package com.iwanvi.nvwa.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.iwanvi.nvwa.web.interceptor.LoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Bean
	public LoginInterceptor getLoginInterceptor() {
		return new LoginInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/")
				.excludePathPatterns("/auth/signin").excludePathPatterns("/auth/signout")
                .excludePathPatterns("/sdkReport/upload").excludePathPatterns("/settle/upload")
				.excludePathPatterns("/upload/**").excludePathPatterns("/synchroAiKa/**")
				.excludePathPatterns("/sdk/**").excludePathPatterns("/app-channels/batchImport")
				.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
	}
}

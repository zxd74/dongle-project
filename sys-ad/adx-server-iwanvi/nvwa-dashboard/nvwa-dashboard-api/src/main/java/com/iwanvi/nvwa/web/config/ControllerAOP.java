package com.iwanvi.nvwa.web.config;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@Aspect
public class ControllerAOP {
	// 环绕aop
	// execution表达式 此表达式表示扫描controller下所有类的所有方法都执行此aop
	@Around("execution (* com.iwanvi.nvwa.web.controller..*.*(..))")
	public Object testAop(ProceedingJoinPoint pro) throws Throwable {
		// 获取response
		HttpServletResponse response = 
				((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
				.getResponse();
		// 核心设置 *为可访问域名
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 执行调用的方法
		Object proceed = pro.proceed();
		return proceed;
	}
}

//package com.dongle.question.config.aop;
//
//import com.dongle.question.utils.ResponseUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * AOP 请求拦截处理
// */
//@Aspect
//@Component
//public class RequestInterceptor {
//
//    @Pointcut("execution (* com.dongle.question.controller..*.*(..))")
//    public void requestPointcut(){}
//
//    @Before("requestPointcut()")
//    public void request(JoinPoint joinPoint){
//
//    }
//
//    @Around("requestPointcut()")
//    public Object response(ProceedingJoinPoint pro) throws Throwable {
//        // 获取response
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        // 核心设置 *为可访问域名
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        // 执行调用的方法
//        return pro.proceed();
//    }
//
//    @Around("requestPointcut()")
//    public void request(ProceedingJoinPoint pro) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        HttpSession session = request.getSession();
//        Object user = session.getAttribute("userinfo");
//        if (user != null){
//            // 用户登录有权限
//            pro.proceed();
//        }else{
//            request.setAttribute("preUrl",request.getRequestURI());
//            request.getRequestDispatcher("/").forward(request,response);
//        }
//
//    }
//}

package com.dongle.question.config;

import com.dongle.question.config.annotation.NotLogin;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 用户验证
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 免登录
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        NotLogin an = method.getAnnotation(NotLogin.class);
        if (an != null){
            return true;
        }

        // 已登录
        HttpSession session = request.getSession();
        Object user = session.getAttribute("userinfo");
        if (user != null){
            return true;
        }

        //未登录
        request.setAttribute("preUrl",request.getRequestURI());
        request.getRequestDispatcher("/").forward(request,response);
        return false;
    }
}

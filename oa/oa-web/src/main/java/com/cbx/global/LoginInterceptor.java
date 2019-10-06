package com.cbx.global;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor  implements HandlerInterceptor{
//    进行拦截，必须登录以后才能进行操作
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        要对登录相关的一些路径进行放行
        String url=request.getRequestURI();
        if(url.toLowerCase().indexOf("login")>=0){
         return true;
        }
//        如果已经登录过，直接放行
        HttpSession session=request.getSession();
        if(session.getAttribute("employee")!=null){
            return true;
        }
        response.sendRedirect("/to_login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

package com.ipermission.common;

import com.ipermission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义请求拦截器
 * 用法：
 * 1、输出接口请求参数 在preHandle打印parameterMap 注意过滤敏感信息
 * 2、接口处理时间
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        long currentTime = System.currentTimeMillis();
        request.setAttribute("startTime",currentTime);
        log.info("request start url:{},paramterMap:{}",requestURI, JsonMapper.obj2String(parameterMap));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI().toString();
        long startTime = (long) request.getAttribute("startTime");
        long currentTime = System.currentTimeMillis();
        Map parameterMap = request.getParameterMap();
        log.info("request finished url:{},cost:{}",requestURI, currentTime-startTime);
    }

}

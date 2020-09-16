package com.ipermission.controller;

import com.ipermission.model.SysUser;
import com.ipermission.service.SysUserService;
import com.ipermission.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        String path = "signin.jsp";
        response.sendRedirect(path);
    }

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ret = request.getParameter("ret");
        SysUser sysUser = sysUserService.findByKeyWord(username);
        String errMsg = "";
        if(StringUtils.isBlank(username)){
            errMsg = "用户名不可以为空";
        }else if(StringUtils.isBlank(password)){
            errMsg = "密码不可以为空";
        }else if(sysUser == null){
            errMsg = "查找不到指定用户";
        }else if(!StringUtils.equals(sysUser.getPassword(), MD5Util.encrypt(password))){
            errMsg = "用户名或密码错误";
        }else if(sysUser.getStatus() != 1){
            errMsg = "用户已被冻结，请联系管理员";
        }else{
            //login success
            request.getSession().setAttribute("user",sysUser);
            if(StringUtils.isNotBlank(ret)){
                response.sendRedirect(ret);
            }else{
                response.sendRedirect("/admin/index.page");
            }
        }
        request.setAttribute("error",errMsg);
        request.setAttribute("username",username);
        if(StringUtils.isNotBlank(ret)){
            request.setAttribute("ret",ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request,response);
    }
}

package com.ipermission.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.ipermission.common.ApplicationContextHelper;
import com.ipermission.common.JsonData;
import com.ipermission.common.RequestHolder;
import com.ipermission.model.SysUser;
import com.ipermission.service.SysCoreService;
import com.ipermission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限拦截Filter
 */
@Slf4j
public class AclControlFilter implements Filter {
    /**权限拦截排除url set集合**/
    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();
    /**无权限访问页面**/
    private static final String noAuthUrl = "/sys/user/noAuth.page";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");//权限排除地址
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(noAuthUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map parameterMap = request.getParameterMap();

        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        String contextPath = request.getContextPath();

        //权限拦截排除
        if(exclusionUrlSet.contains(servletPath)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //获取用户
        SysUser currentUser = RequestHolder.getCurrentUser();
        if(currentUser == null){
            log.info("为登录访问{}，访问参数{}",servletPath, JsonMapper.obj2String(parameterMap));
            noAuth(request,response);
            return;
        }

        //用户是否有访问url的权限
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if(!sysCoreService.hasUrlAcl(servletPath)){
            log.info("用户{}访问{}无权限，访问参数{}",JsonMapper.obj2String(currentUser),servletPath,
                    JsonMapper.obj2String(parameterMap));
            noAuth(request,response);
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
        return ;
    }

    /**
     * 无权限操作
     * @param request
     * @param response
     */
    public void noAuth(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        if(StringUtils.endsWith(servletPath,".json")){
            JsonData fail = JsonData.fail("无访问权限，如需要访问请联系管理员");
            response.setHeader("Content-Type","application/json");
            response.getWriter().println(JsonMapper.obj2String(fail));
            return;
        }else{
            clientRedirect(noAuthUrl,response);
            return;
        }
    }

    public void clientRedirect(String url,HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }
}

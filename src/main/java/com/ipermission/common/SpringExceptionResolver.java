package com.ipermission.common;

import com.ipermission.exception.ParamException;
import com.ipermission.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";
        //规定所有json请求以.json结尾
        if(StringUtils.endsWith(url,".json")){
            if(e instanceof PermissionException || e instanceof ParamException){
                log.error("unknown json exception,url:{}",url,e);
                JsonData fail = JsonData.fail(e.getMessage());
                mv = new ModelAndView("jsonView",fail.toMap());
            }else{
                log.error("unknown json exception,url:{}",url,e);
                JsonData fail = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView",fail.toMap());
            }

        }
        //规定所有页面请求 以.page结尾
        else if(StringUtils.endsWith(url,".page")){
            log.error("unknown page exception,url:{}",url,e);
            JsonData fail = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception",fail.toMap());
        }else{
            log.error("unknown exception,url:{}",url,e);
            JsonData fail = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView",fail.toMap());
        }
        return mv;
    }

}

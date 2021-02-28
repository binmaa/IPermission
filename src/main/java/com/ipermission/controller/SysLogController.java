package com.ipermission.controller;

import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.common.JsonData;
import com.ipermission.model.SysLogWithBLOBs;
import com.ipermission.param.SearchLogParam;
import com.ipermission.service.SysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/log")
public class SysLogController {
    @Resource
    private SysLogService sysLogService;

    @RequestMapping("/log.page")
    public ModelAndView page(){
        return new ModelAndView("log");
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData searchPage(SearchLogParam param, PageQuery page){
        PageResult<SysLogWithBLOBs> pageResult = sysLogService.searchPage(param, page);
        return JsonData.success(pageResult);
    }

    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(int id){
        sysLogService.recover(id);
        return JsonData.success();
    }

}

package com.ipermission.controller;

import com.ipermission.common.JsonData;
import com.ipermission.dto.AclModuleLevelDto;
import com.ipermission.dto.DeptLevelDto;
import com.ipermission.param.AclModuleParam;
import com.ipermission.service.SysTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/aclModule.page")
    public ModelAndView dept(){
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(AclModuleParam param){

        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(AclModuleParam param){

        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<AclModuleLevelDto> aclModuleTree = sysTreeService.aclModuleTree();
        return JsonData.success(aclModuleTree);
    }
}

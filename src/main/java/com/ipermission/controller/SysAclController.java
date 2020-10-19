package com.ipermission.controller;

import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.common.JsonData;
import com.ipermission.dao.SysAclMapper;
import com.ipermission.model.SysAcl;
import com.ipermission.param.AclParam;
import com.ipermission.service.SysAclService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/acl")
public class SysAclController {
    @Resource
    private SysAclService sysAclService;

    @RequestMapping("/save.json")
    public JsonData save(AclParam aclParam){
        sysAclService.save(aclParam);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    public JsonData update(AclParam aclParam){
        sysAclService.update(aclParam);
        return JsonData.success();
    }
    @RequestMapping("/page.json")
    public JsonData page(@RequestParam("aclModuleId") int aclModuleId, PageQuery pageQuery){
        PageResult<SysAcl> result = sysAclService.getPageByAclModuleId(aclModuleId, pageQuery);
        return JsonData.success(result);
    }
}

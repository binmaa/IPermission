package com.ipermission.controller;

import com.google.common.collect.Maps;
import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.common.JsonData;
import com.ipermission.dao.SysAclMapper;
import com.ipermission.model.SysAcl;
import com.ipermission.model.SysRole;
import com.ipermission.param.AclParam;
import com.ipermission.service.SysAclService;
import com.ipermission.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/acl")
public class SysAclController {
    @Resource
    private SysAclService sysAclService;
    @Resource
    private SysRoleService sysRoleService;

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
    /**
     * 权限分配的用户和角色
     * @param aclId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId){
        Map<String,Object> res = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        res.put("roles",roleList);
        res.put("users",sysRoleService.getUserListByRoleList(roleList));
        return JsonData.success(res);
    }

}

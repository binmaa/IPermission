package com.ipermission.controller;

import com.google.common.collect.Maps;
import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.common.JsonData;
import com.ipermission.model.SysUser;
import com.ipermission.param.UserParam;
import com.ipermission.service.SysRoleService;
import com.ipermission.service.SysTreeService;
import com.ipermission.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("/noAuth.page")
    public ModelAndView noAuth(){
        return new ModelAndView("noAuth");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param){
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param){
        sysUserService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery){
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

    /**
     * 用户拥有的权限
     * @param userId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId){
        Map<String,Object> res = Maps.newHashMap();
        res.put("acls",sysTreeService.userAclTree(userId));
        res.put("role",sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(res);
    }

}

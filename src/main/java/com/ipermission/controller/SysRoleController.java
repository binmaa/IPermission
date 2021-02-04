package com.ipermission.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ipermission.common.JsonData;
import com.ipermission.dto.AclModuleLevelDto;
import com.ipermission.model.SysUser;
import com.ipermission.param.RoleParam;
import com.ipermission.service.*;
import com.ipermission.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleAclService sysRoleAclService;

    @Resource
    private SysRoleUserService sysRoleUserService;

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/role.page")
    public ModelAndView page(){
        return new ModelAndView("role");
     }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(RoleParam roleParam){
        sysRoleService.save(roleParam);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(RoleParam roleParam){
        sysRoleService.update(roleParam);
        return JsonData.success();
    }
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(Integer roleId){
        sysRoleService.delete(roleId);
        return JsonData.success();
    }
    @ResponseBody
    @RequestMapping("/list.json")
    public JsonData list(){
        return JsonData.success(sysRoleService.getAllRole());
    }

    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId){
        List<AclModuleLevelDto> aclModuleLevelDtos = sysTreeService.roleTree(roleId);
        return JsonData.success(aclModuleLevelDtos);
    }

    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId,@RequestParam("aclIds")String aclIds){
        List<Integer> aclIdList = StringUtils.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId,aclIdList);
        return JsonData.success();
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId,@RequestParam("userIds")String userIds){
        List<Integer> userIdList = StringUtils.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId,userIdList);
        return JsonData.success();
    }

    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){
        List<SysUser> selectedUserList = sysRoleUserService.getUserListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();

        Set<Integer> selectedUserId = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for(SysUser sysUser:allUserList){
            if(sysUser.getStatus() == 1 && !selectedUserId.contains(sysUser.getId())){
                unselectedUserList.add(sysUser);
            }
        }
        //集合流过滤
        //过滤选中之后 状态不为1的用户
        //selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected",selectedUserList);
        map.put("unselected",unselectedUserList);
        return JsonData.success(map);
    }

}

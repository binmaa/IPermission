package com.ipermission.service;

import com.google.common.collect.Lists;
import com.ipermission.beans.CacheKeyConstans;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysAclMapper;
import com.ipermission.dao.SysRoleAclMapper;
import com.ipermission.dao.SysRoleUserMapper;
import com.ipermission.model.SysAcl;
import com.ipermission.model.SysUser;
import com.ipermission.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysCoreService {
    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysCacheService sysCacheService;

    /**
     * 当前用户权限
     * @return
     */
    public List<SysAcl> getCurrentUserAclList(){
        Integer userId = RequestHolder.getCurrentUser().getId();
        List<SysAcl> userAclList = this.getUserAclList(userId);
        return userAclList;
    }

    /**
     * 从缓存中获取当前用户权限
     * @return
     */
    public List<SysAcl> getCurrentUserAclListFromCache(){
        Integer userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstans.USER_ACLS, String.valueOf(userId));
        if(StringUtils.isBlank(cacheValue)){
            List<SysAcl> userAclList = this.getCurrentUserAclList();
            if(CollectionUtils.isNotEmpty(userAclList)){
                sysCacheService.saveCache(JsonMapper.obj2String(userAclList),6000,CacheKeyConstans.USER_ACLS,String.valueOf(userId));
            }
            return userAclList;
        }
        return JsonMapper.string2Object(cacheValue, new TypeReference<List<SysAcl>>() {
        });

    }

    /**
     * 角色权限
     * @param roleId
     * @return
     */
    public List<SysAcl> getRoleAclList(int roleId){
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdByUserRoleIdList(Lists.newArrayList(roleId));
        if(CollectionUtils.isEmpty(aclIdList)){
            return Lists.newArrayList();
        }
        return sysAclMapper.getAclByAclIdList(aclIdList);
    }

    /**
     * 用户的权限
     * @param userId
     * @return
     */
    public List<SysAcl> getUserAclList(int userId){
        if(isSupperAdmin()){
            return sysAclMapper.getAll();
        }
        //获取用户所有角色id
        List<Integer> userRoleIdlList = sysRoleUserMapper.getRoleIdByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleIdlList)){
            return Lists.newArrayList();
        }
        //获取用户所有权限id
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdByUserRoleIdList(userRoleIdlList);
        if(CollectionUtils.isEmpty(userAclIdList)){
            return Lists.newArrayList();
        }
        //获取用户所有权限
        return sysAclMapper.getAclByAclIdList(userAclIdList);
    }

    /**
     * 是否为超级管理员
     * @return
     */
    public boolean isSupperAdmin(){
        //超级管理源规则
        SysUser currentUser = RequestHolder.getCurrentUser();
        if(currentUser.getMail().contains("admin")){
            return true;
        }
        return false;
    }

    /**
     * 是否有url访问权限
     * @param servletPath
     * @return
     */
    public boolean hasUrlAcl(String servletPath) {
        if(isSupperAdmin()){
            return true;
        }
        List<SysAcl> sysAclList = sysAclMapper.getAclByUrl(servletPath);//拦截url的权限
        //Set<SysAcl> sysAclValid = sysAclList.stream().filter(sysAcl -> sysAcl.getStatus() == 1).collect(Collectors.toSet());
        if(CollectionUtils.isEmpty(sysAclList)){//没有权限是关于这个url的 说明有url的访问权限
            return true;
        }
        //List<SysAcl> currentUserAclList = this.getCurrentUserAclList();//当前用户权限
        List<SysAcl> currentUserAclList = this.getCurrentUserAclListFromCache();
        Set<Integer> usrAclIdSet = currentUserAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        //规则：只要有一个权限点有权限，就认为有权限访问
        boolean hasValidAcl = false;//是否存在有效的权限点 默认不存在
        for(SysAcl sysAcl:sysAclList){
            if(sysAcl == null || sysAcl.getStatus() != 1){//当前权限不生效
                continue;
            }
            hasValidAcl = true;//存在有效的权限点拦截 存在就要有权限
            if(usrAclIdSet.contains(sysAcl.getId())){//有权限
                return true;
            }
        }
        if(!hasValidAcl){//没有有效的权限点 放行
            return true;
        }
        return false;//有有效的权限点 并且没有被授权
    }
}

package com.ipermission.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysRoleAclMapper;
import com.ipermission.dao.SysRoleMapper;
import com.ipermission.dao.SysRoleUserMapper;
import com.ipermission.dao.SysUserMapper;
import com.ipermission.exception.ParamException;
import com.ipermission.model.SysRole;
import com.ipermission.model.SysUser;
import com.ipermission.param.RoleParam;
import com.ipermission.util.BeanValidator;
import com.ipermission.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    public void save(RoleParam roleParam){
        BeanValidator.check(roleParam);
        if(checkExist(roleParam.getName(),roleParam.getId())){
            throw new ParamException("当前角色名已存在");
        }
        SysRole sysRole = SysRole.builder().name(roleParam.getName()).type(roleParam.getType()).
                status(roleParam.getStatus()).remark(roleParam.getRemark()).build();
        sysRole.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperaterTime(new Date());
        sysRoleMapper.insertSelective(sysRole);
    }

    public void update(RoleParam roleParam){
        BeanValidator.check(roleParam);
        if(checkExist(roleParam.getName(),roleParam.getId())){
            throw new ParamException("当前角色名已存在");
        }
        SysRole after = sysRoleMapper.selectByPrimaryKey(roleParam.getId());
        Preconditions.checkNotNull(after,"待更新的角色不存在");
        SysRole sysRole = SysRole.builder().id(roleParam.getId()).name(roleParam.getName()).type(roleParam.getType()).
                status(roleParam.getStatus()).remark(roleParam.getRemark()).build();
        sysRole.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperaterTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Transactional
    public void delete(Integer roleId){
        sysRoleAclMapper.deleteByRoleId(roleId);
        sysRoleUserMapper.deleteByRoleId(roleId);
        sysRoleMapper.deleteByPrimaryKey(roleId);
    }

    public List<SysRole> getAllRole(){
        return sysRoleMapper.getAll();
    }

    private boolean checkExist(String name,Integer id){
        return sysRoleMapper.countByName(name,id) > 0;
    }

    /**
     * 用户被授予的角色
     * @param userId
     * @return
     */
    public List<SysRole> getRoleListByUserId(int userId){
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdByUserId(userId);
        if(CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        List<SysRole> roleList = sysRoleMapper.getRoleByRoleIdList(roleIdList);
        return roleList;
    }

    /**
     * 权限分配的角色
     * @param aclId
     * @return
     */
    public List<SysRole> getRoleListByAclId(int aclId){
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if(CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        List<SysRole> roleList = sysRoleMapper.getRoleByRoleIdList(roleIdList);
        return roleList;
    }

    /**
     * 查询授予角色的用户列表
     * @param roleList
     * @return
     */
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList){
        if(CollectionUtils.isEmpty(roleList)){
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if(CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        List<SysUser> userList = sysUserMapper.getUserListByIds(userIdList);
        return userList;
    }

}

package com.ipermission.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysRoleUserMapper;
import com.ipermission.dao.SysUserMapper;
import com.ipermission.model.SysRoleAcl;
import com.ipermission.model.SysRoleUser;
import com.ipermission.model.SysUser;
import com.ipermission.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    public List<SysUser> getUserListByRoleId(int roleId){
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        return sysUserMapper.getUserListByIds(userIdList);
    }

    public void changeRoleUsers(int roleId,List<Integer> userIdList){
        //获取角色用户
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if(userIdList.size() == originUserIdList.size()){
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if(CollectionUtils.isNotEmpty(originUserIdSet)){
                return;
            }
        }
        updateRoleUsers(roleId,userIdList);
    }
    @Transactional
    public void updateRoleUsers(int roleId,List<Integer> userIdList){
        sysRoleUserMapper.deleteByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)){
            return;
        }
        List<SysRoleUser> sysRoleUserList = Lists.newArrayList();
        for (Integer userId:userIdList){
            SysRoleUser sysRoleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operator(RequestHolder.getCurrentUser().getUsername()).
                    operaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operaterTime(new Date()).build();
            sysRoleUserList.add(sysRoleUser);
        }
        sysRoleUserMapper.batchInsert(sysRoleUserList);
    }

}

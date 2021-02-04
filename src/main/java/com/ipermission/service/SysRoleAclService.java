package com.ipermission.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysRoleAclMapper;
import com.ipermission.model.SysRoleAcl;
import com.ipermission.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleAclService {
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList){
        //获取角色原权限点
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdByUserRoleIdList(Lists.newArrayList(roleId));
        if(aclIdList.size() == originAclIdList.size()){
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if(CollectionUtils.isNotEmpty(originAclIdSet)){
                return;
            }
        }
        updateRoleAcls(roleId,aclIdList);
    }

    @Transactional
    public void updateRoleAcls(int roleId,List<Integer> aclIdList){
        sysRoleAclMapper.deleteByRoleId(roleId);
        if(CollectionUtils.isEmpty(aclIdList)){
            return;
        }
        List<SysRoleAcl> sysRoleAclList = Lists.newArrayList();
        for (Integer aclId:aclIdList){
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operator(RequestHolder.getCurrentUser().getUsername()).
                    operaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operaterTime(new Date()).build();
            sysRoleAclList.add(sysRoleAcl);
        }
        sysRoleAclMapper.batchInsert(sysRoleAclList);
    }

}

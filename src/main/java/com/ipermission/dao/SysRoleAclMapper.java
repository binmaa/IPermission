package com.ipermission.dao;

import com.ipermission.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> getAclIdByUserRoleIdList(@Param("userRoleIdlList")List<Integer> userRoleIdlList);

    void deleteByRoleId(@Param("roleId") int roleId);

    void batchInsert(@Param("sysRoleAclList")List<SysRoleAcl> sysRoleAclList);

    List<Integer> getRoleIdListByAclId(@Param("aclId")int aclId);
}
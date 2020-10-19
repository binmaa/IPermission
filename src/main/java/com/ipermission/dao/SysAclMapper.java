package com.ipermission.dao;

import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByName(@Param("aclModuleId") int aclModuleId,String name,int id);

    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId,@Param("page") PageQuery page);
}
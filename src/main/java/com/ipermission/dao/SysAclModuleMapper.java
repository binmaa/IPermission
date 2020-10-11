package com.ipermission.dao;

import com.ipermission.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByNameAndParent(@Param("parentId")Integer parentId, @Param("aclModuleName")String name, @Param("aclModuleId")Integer id);

    void batchUpdateLevel(@Param("sysAlcModuleList")List<SysAclModule> sysAlcModuleList);

    List<SysAclModule> getChildAclModuleListByLevel(@Param("level")String level);

    List<SysAclModule> getAllAclModule();
}
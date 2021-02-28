package com.ipermission.dao;

import com.ipermission.beans.PageQuery;
import com.ipermission.dto.SearchLogDto;
import com.ipermission.model.SysLog;
import com.ipermission.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    int countBySearchDto(@Param("search") SearchLogDto search);

    List<SysLogWithBLOBs> getPageListBySearchDto(@Param("search") SearchLogDto search, @Param("page") PageQuery page);
}
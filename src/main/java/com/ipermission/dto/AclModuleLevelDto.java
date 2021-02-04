package com.ipermission.dto;

import com.google.common.collect.Lists;
import com.ipermission.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule{
    /**当前权限模块下的权限模块列表*/
    private List<AclModuleLevelDto> aclModuleList= Lists.newArrayList();
    /**当前权限模块下的权限列表**/
    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt (SysAclModule sysAclModule){
        AclModuleLevelDto aclModuleLevelDto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule, aclModuleLevelDto);
        return aclModuleLevelDto;
    }
}

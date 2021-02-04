package com.ipermission.dto;


import com.ipermission.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString
public class AclDto extends SysAcl {
    //权限是否被选中
    private boolean checked = false;
    //是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl){
        AclDto aclDto = new AclDto();
        BeanUtils.copyProperties(acl,aclDto);
        return aclDto;
    }
}

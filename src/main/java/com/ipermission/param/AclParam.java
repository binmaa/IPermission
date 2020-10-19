package com.ipermission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AclParam {
    private Integer id;
    @NotBlank(message = "权限点名称不能为空")
    @Length(min = 2,max = 20,message = "权限点名称要在2-20个字符之间")
    private String name;
    @NotNull(message = "必须指定权限模块")
    private Integer aclModuleId;
    @Length(min=6,max=100,message = "权限点URL长度需要在6-100个字符之间")
    private String url;
    @NotNull(message = "必须指定权限点类型")
    @Min(value = 1,message = "权限点类型不合法")
    @Max(value = 3,message = "权限点类型不合法")
    private Integer type;
    @NotNull(message = "必须指定权限点顺序")
    private Integer seq;
    @NotNull(message = "必须指定权限点状态")
    @Min(value = 1,message = "权限点状态不合法")
    @Max(value = 2,message = "权限点状态不合法")
    private Integer status;
    @Length(min = 0,max = 200,message ="备注长度0-200个字符")
    private String remark;
}

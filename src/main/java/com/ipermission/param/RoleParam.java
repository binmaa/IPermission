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
public class RoleParam {
    private Integer id;
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2,max = 20,message = "角色名称要在2-20个字符之间")
    private String name;
    @NotNull(message = "必须指定角色类型")
    @Min(value = 1,message = "角色类型不合法")
    @Max(value = 2,message = "角色类型不合法")
    private Integer type = 1;
    @NotNull(message = "必须指定角色状态")
    @Min(value = 0,message = "角色状态不合法")
    @Max(value = 1,message = "角色状态不合法")
    private Integer status;
    @Length(min = 0,max = 200,message ="备注长度0-200个字符")
    private String remark;
}

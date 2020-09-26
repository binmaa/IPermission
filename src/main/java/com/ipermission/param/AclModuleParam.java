package com.ipermission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AclModuleParam {
    private Integer id;

    @Length(min=2,max=20,message = "权限模块名称要在2-20个字之间")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "权限模展示顺序不能为空")
    private Integer seq;

    @NotNull(message = "权限模状态不能为空")
    @Min(value = 0,message = "权限状态不合法")
    @Max(value = 1,message = "权限状态不合法")
    private Integer status;

    @Length(max=200,message = "权限模块备注要在200个字之内")
    private String remark;
}

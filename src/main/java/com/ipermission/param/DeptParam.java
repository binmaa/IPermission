package com.ipermission.param;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
public class DeptParam {
    private Integer id;
    @NotBlank(message = "部门名不能为空")
    @Length(max = 15,min = 2,message = "部门名称长度2-15个字符")
    private String name;
    private Integer parentId;
    @NotNull(message="显示顺序不能为空")
    private Integer seq;
    @Length(max=150,message = "备注长度最长为150个字符")
    private String remark;
}

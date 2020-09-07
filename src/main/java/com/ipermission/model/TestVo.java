package com.ipermission.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TestVo {
    @NotNull
    @Max(value=10,message = "最大不能超过10")
    private String id;
    @NotBlank
    private String msg;
    @NotEmpty
    private List<String> strList;
}

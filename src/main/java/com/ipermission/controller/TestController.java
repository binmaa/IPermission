package com.ipermission.controller;

import com.ipermission.common.JsonData;
import com.ipermission.dao.SysDeptMapper;
import com.ipermission.exception.PermissionException;
import com.ipermission.model.TestVo;
import com.ipermission.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData test(){
        log.info("hello");
        throw new PermissionException("test exception");
        //return JsonDate.success("hello permission");
    }

    @RequestMapping("/testVo.json")
    @ResponseBody
    public JsonData testVo(TestVo testVo){
        log.info("validator");
        Map<String, String> errors = BeanValidator.validateObject(testVo);
        if(errors!=null&&errors.size()!=0){
            for(Map.Entry<String,String> entry:errors.entrySet()){
                log.error("{}->{}",entry.getKey(),entry.getValue());
            }
        }
        return JsonData.success("hello validator");
    }

    @RequestMapping("/test.json")
    @ResponseBody
    public JsonData test2(String level){

        return JsonData.success(sysDeptMapper.getChildDeptListByLevel(level));
    }

}

package com.ipermission.controller;

import com.ipermission.common.JsonData;
import com.ipermission.dto.DeptLevelDto;
import com.ipermission.param.DeptParam;
import com.ipermission.service.SysDeptService;
import com.ipermission.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {
    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    public ModelAndView dept(){
        return new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param){
        sysDeptService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteDept(Integer deptId){
        sysDeptService.delete(deptId);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> deptTree = sysTreeService.deptTree();
        return JsonData.success(deptTree);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param){
        sysDeptService.update(param);
        return JsonData.success();
    }

}

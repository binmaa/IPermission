package com.ipermission.service;

import com.google.common.base.Preconditions;
import com.ipermission.dao.SysDeptMapper;
import com.ipermission.exception.ParamException;
import com.ipermission.model.SysDept;
import com.ipermission.param.DeptParam;
import com.ipermission.util.BeanValidator;
import com.ipermission.util.LevelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    public void save(DeptParam deptParam){
        BeanValidator.check(deptParam);
        if(checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }else{
            SysDept sysDept = SysDept.builder().name(deptParam.getName()).parentId(deptParam.getParentId())
                    .seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
            sysDept.setLevel(LevelUtils.calculateLevel(
                    this.getLevel(deptParam.getParentId()),deptParam.getParentId()));
            sysDept.setOperator("system-save");//todo
            sysDept.setOperaterIp("127.0.0.1");//todo
            sysDept.setOperaterTime(new Date());//todo
            sysDeptMapper.insert(sysDept);
        }

    }

    public void update(DeptParam deptParam){
        BeanValidator.check(deptParam);
        if(checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");
        SysDept after = SysDept.builder().id(deptParam.getId()).name(deptParam.getName()).parentId(deptParam.getParentId())
                .seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
        after.setLevel(LevelUtils.calculateLevel(
                this.getLevel(deptParam.getParentId()),deptParam.getParentId()));

        after.setOperator("system-update");//todo
        after.setOperaterIp("127.0.0.1");//todo
        after.setOperaterTime(new Date());//todo
        if(this.getLevel(deptParam.getParentId()).startsWith(before.getLevel()) &&
                !StringUtils.equals(this.getLevel(deptParam.getParentId()),before.getLevel())){
            throw new ParamException("当前部门不允许挂接到自己的子部门下");
        }

        updateWithChild(before,after);
    }

    /**
     * 更新部门
     * @param before
     * @param after
     */
    @Transactional
    public void updateWithChild(SysDept before, SysDept after) {
        String newDeptLevelPrefix = after.getLevel();
        String oldDeptLevelPrefix = before.getLevel();
        if(!StringUtils.equals(newDeptLevelPrefix,oldDeptLevelPrefix)){
            //获取当前部门的子部门
            List<SysDept> childDeptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if(CollectionUtils.isNotEmpty(childDeptList)){
                for(SysDept sysDept : childDeptList){
                    String level = sysDept.getLevel();
                    if(level.indexOf
                            (LevelUtils.calculateLevel(oldDeptLevelPrefix,before.getId())) == 0){//以原部门level为前缀修改
                        level = newDeptLevelPrefix + level.substring(oldDeptLevelPrefix.length());
                        sysDept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(childDeptList);
            }

        }

        sysDeptMapper.updateByPrimaryKey(after);

    }

    /**
     * 当前部门是否存在
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    public boolean checkExist(Integer parentId,String deptName,Integer deptId){
        return sysDeptMapper.countByNameAndParent(parentId,deptName,deptId)>0;
    }

    /**
     * 获取部门level
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if(sysDept == null){//id为deptId的部门不存在 说明是顶级
            return null;
        }
        return sysDept.getLevel();

    }
}

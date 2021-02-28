package com.ipermission.service;

import com.google.common.base.Preconditions;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysDeptMapper;
import com.ipermission.dao.SysUserMapper;
import com.ipermission.exception.ParamException;
import com.ipermission.model.SysDept;
import com.ipermission.param.DeptParam;
import com.ipermission.util.BeanValidator;
import com.ipermission.util.IpUtil;
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

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysLogService sysLogService;

    public void save(DeptParam deptParam){
        BeanValidator.check(deptParam);
        if(checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }else{
            SysDept sysDept = SysDept.builder().name(deptParam.getName()).parentId(deptParam.getParentId())
                    .seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
            sysDept.setLevel(LevelUtils.calculateLevel(
                    this.getLevel(deptParam.getParentId()),deptParam.getParentId()));
            sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
            sysDept.setOperaterIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
            sysDept.setOperaterTime(new Date());
            sysDeptMapper.insert(sysDept);
            sysLogService.saveDeptLog(null,sysDept);
        }
    }

    public void delete(Integer deptId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(sysDept,"待删除的部门不存在，无法删除");
        if(sysDeptMapper.countByParentId(deptId) > 0){//存在子部门
            throw new ParamException("当前部门下存在子部门，无法删除");
        }
        if(sysUserMapper.countByDeptId(deptId) > 0){//部门存在用户
            throw new ParamException("当前部门下存在用户，无法删除");
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
        sysLogService.saveDeptLog(sysDept,null);
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

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperaterIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperaterTime(new Date());
        if(this.getLevel(deptParam.getParentId()).startsWith(before.getLevel()) &&
                !StringUtils.equals(this.getLevel(deptParam.getParentId()),before.getLevel())){
            throw new ParamException("当前部门不允许挂接到自己的子部门下");
        }

        updateWithChild(before,after);
        sysLogService.saveDeptLog(before,after);
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

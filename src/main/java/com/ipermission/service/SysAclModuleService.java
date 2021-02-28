package com.ipermission.service;

import com.google.common.base.Preconditions;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysAclMapper;
import com.ipermission.dao.SysAclModuleMapper;
import com.ipermission.exception.ParamException;
import com.ipermission.model.SysAclModule;
import com.ipermission.param.AclModuleParam;
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
public class SysAclModuleService {
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysLogService sysLogService;

    public void save(AclModuleParam param){
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }else{
            SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId())
                    .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark()).build();
            aclModule.setLevel(LevelUtils.calculateLevel(
                    this.getLevel(param.getParentId()),param.getParentId()));
            aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
            aclModule.setOperaterIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
            aclModule.setOperaterTime(new Date());
            sysAclModuleMapper.insert(aclModule);
            sysLogService.saveAclModuleLog(null,aclModule);
        }
    }
    public void update(AclModuleParam param){
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的权限模块不存在");
        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark()).build();
        after.setLevel(LevelUtils.calculateLevel(
                this.getLevel(param.getParentId()),param.getParentId()));

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperaterIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperaterTime(new Date());
        if(this.getLevel(param.getParentId()).startsWith(before.getLevel()) &&
                !StringUtils.equals(this.getLevel(param.getParentId()),before.getLevel())){
            throw new ParamException("当前权限模块不允许挂接到自己的子模块下");
        }
        updateWithChild(before,after);
        sysLogService.saveAclModuleLog(before,after);
    }
    
    public void delete(Integer aclModuleId){
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(sysAclModule,"删除的权限模块不存在");
        if(sysAclModuleMapper.countByParent(aclModuleId) > 0){
            throw new ParamException("权限模块下存在子模块");
        }
        if(sysAclMapper.countByAclModuleId(aclModuleId) > 0){
            throw new ParamException("权限模块下存在权限");
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
        sysLogService.saveAclModuleLog(sysAclModule,null);
    }

    @Transactional
    public void updateWithChild(SysAclModule before, SysAclModule after) {
        String newAclModuleLevelPrefix = after.getLevel();
        String oldAclModuleLevelPrefix = before.getLevel();
        if(!StringUtils.equals(newAclModuleLevelPrefix,oldAclModuleLevelPrefix)){
            //获取当前权限模块的子模块
            List<SysAclModule> childDeptList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel());
            if(CollectionUtils.isNotEmpty(childDeptList)){
                for(SysAclModule sysAclModule : childDeptList){
                    String level = sysAclModule.getLevel();
                    if(level.indexOf
                            (LevelUtils.calculateLevel(oldAclModuleLevelPrefix,before.getId())) == 0){//以原部门level为前缀修改
                        level = newAclModuleLevelPrefix + level.substring(oldAclModuleLevelPrefix.length());
                        sysAclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(childDeptList);
            }
        }
        sysAclModuleMapper.updateByPrimaryKey(after);

    }

    /**
     * 当前权限模块是否存在
     * @param parentId
     * @param name
     * @param id
     * @return
     */
    public boolean checkExist(Integer parentId,String name,Integer id){
        return sysAclModuleMapper.countByNameAndParent(parentId,name,id) > 0;
    }

    /**
     * 获取权限模块level
     * @param id
     * @return
     */
    private String getLevel(Integer id){
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(id);
        if(sysAclModule == null){//为id的权限模块不存在 说明是顶级
            return null;
        }
        return sysAclModule.getLevel();

    }
}

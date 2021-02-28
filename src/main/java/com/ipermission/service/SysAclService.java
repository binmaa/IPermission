package com.ipermission.service;

import cn.hutool.db.Page;
import com.google.common.base.Preconditions;
import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.common.JsonData;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysAclMapper;
import com.ipermission.exception.ParamException;
import com.ipermission.model.SysAcl;
import com.ipermission.model.SysLog;
import com.ipermission.param.AclParam;
import com.ipermission.util.BeanValidator;
import com.ipermission.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclService {
    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysLogService sysLogService;

    public void save(AclParam param){
        BeanValidator.check(param);
        if(checkExist(param.getAclModuleId(),param.getName(),param.getId())){
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId())
                .url(param.getUrl()).type(param.getType()).status(param.getStatus()).seq(param.getSeq())
                .remark(param.getRemark()).build();
        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperaterTime(new Date());
        acl.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclMapper.insert(acl);
        sysLogService.saveAclLog(null,acl);
    }

    public void update(AclParam param){
        BeanValidator.check(param);
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"更新的权限点不存在");
        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId())
                .url(param.getUrl()).type(param.getType()).status(param.getStatus()).seq(param.getSeq())
                .remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperaterTime(new Date());
        after.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before,after);
    }

    public boolean checkExist(int aclModuleId,String name,Integer id){
        return sysAclMapper.countByName(aclModuleId,name,id)>0;
    }

    public String generateCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date())+(int)Math.random()*100;
    }

    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery){
        BeanValidator.check(pageQuery);
        int aclCount = sysAclMapper.countByAclModuleId(aclModuleId);
        if(aclCount > 0){
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            return PageResult.<SysAcl>builder().total(aclCount).data(aclList).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

}

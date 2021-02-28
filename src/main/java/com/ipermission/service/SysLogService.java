package com.ipermission.service;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Preconditions;
import com.ipermission.beans.LogType;
import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.*;
import com.ipermission.dto.SearchLogDto;
import com.ipermission.exception.ParamException;
import com.ipermission.model.*;
import com.ipermission.param.SearchLogParam;
import com.ipermission.util.BeanValidator;
import com.ipermission.util.IpUtil;
import com.ipermission.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleAclService sysRoleAclService;
    @Resource
    private SysRoleUserService sysRoleUserService;


    public void recover(int logId){
        SysLogWithBLOBs sysLog = sysLogMapper.selectByPrimaryKey(logId);
        Preconditions.checkNotNull(sysLog,"待还原记录不存在");
        //根据类型还原
        switch (sysLog.getType()){
            case LogType.TYPE_DEPT:
                SysDept beforeSDept = sysDeptMapper.selectByPrimaryKey(sysLog.getTargetId());//获取记录原始内容 新增/更新：就是日志记录内容 删除：不存在了
                Preconditions.checkNotNull(beforeSDept,"待换原部门不存在了");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysDept afterDept = JsonMapper.string2Object(sysLog.getOldValue(), new TypeReference<SysDept>() {
                });//记录中的原值
                afterDept.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterDept.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterDept.setOperaterTime(new Date());
                sysDeptMapper.updateByPrimaryKeySelective(afterDept);
                saveDeptLog(beforeSDept,afterDept);
                break;
            case LogType.TYPE_USER:
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());//获取记录原始内容 新增/更新：就是日志记录内容 删除：不存在了
                Preconditions.checkNotNull(beforeUser,"待换原用户不存在了");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysUser afterUser = JsonMapper.string2Object(sysLog.getOldValue(), new TypeReference<SysUser>() {
                });//记录中的原值
                afterUser.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterUser.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterUser.setOperaterTime(new Date());
                sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser,afterUser);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLog.getTargetId());//获取记录原始内容 新增/更新：就是日志记录内容 删除：不存在了
                Preconditions.checkNotNull(beforeAclModule,"待换原权限模块不存在了");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAclModule afterAclModule = JsonMapper.string2Object(sysLog.getOldValue(), new TypeReference<SysAclModule>() {
                });//记录中的原值
                afterAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAclModule.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAclModule.setOperaterTime(new Date());
                sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);
                saveAclModuleLog(beforeAclModule,afterAclModule);
                break;
            case LogType.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLog.getTargetId());//获取记录原始内容 新增/更新：就是日志记录内容 删除：不存在了
                Preconditions.checkNotNull(beforeAcl,"待换原权限不存在了");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAcl afterAcl = JsonMapper.string2Object(sysLog.getOldValue(), new TypeReference<SysAcl>() {
                });//记录中的原值
                afterAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAcl.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperaterTime(new Date());
                sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl,afterAcl);
                break;
            case LogType.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());//获取记录原始内容 新增/更新：就是日志记录内容 删除：不存在了
                Preconditions.checkNotNull(beforeRole,"待换原角色不存在了");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysRole afterRole = JsonMapper.string2Object(sysLog.getOldValue(), new TypeReference<SysRole>() {
                });//记录中的原值
                afterRole.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterRole.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperaterTime(new Date());
                sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole,afterRole);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole roleAcl = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(roleAcl,"带还原角色不存在了");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增和删除操作不做还原");
                }
                List<Integer> afterRoleAcl = JsonMapper.string2Object(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                });
                sysRoleAclService.changeRoleAcls(roleAcl.getId(),afterRoleAcl);
                break;
            case LogType.TYPE_ROLE_USER:
                SysRole roleUser = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(roleUser,"带还原角色不存在了");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增和删除操作不做还原");
                }
                List<Integer> afterRoleUser = JsonMapper.string2Object(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                });
                sysRoleUserService.changeRoleUsers(roleUser.getId(),afterRoleUser);
                break;
            default:
                break;
        }
    }

    public PageResult<SysLogWithBLOBs> searchPage(SearchLogParam param,PageQuery pageQuery){
        BeanValidator.check(pageQuery);
        SearchLogDto searchLogDto = new SearchLogDto();
        searchLogDto.setType(param.getType());
        if(StringUtils.isNotBlank(param.getAfterSeg())){
            searchLogDto.setAfterSeg("%"+param.getAfterSeg()+"%");
        }
        if(StringUtils.isNotBlank(param.getAfterSeg())){
            searchLogDto.setBeforeSeg("%"+param.getBeforeSeg()+"%");
        }
        if(StringUtils.isNotBlank(param.getOperator())){
            searchLogDto.setOperator("%"+param.getOperator()+"%");
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.isNotBlank(param.getFromTime())){
                searchLogDto.setFromTime(format.parse(param.getFromTime()));
            }
            if(StringUtils.isNotBlank(param.getToTime())){
                searchLogDto.setToTime(format.parse(param.getToTime()));
            }
        }catch (Exception e){
            throw new ParamException("传入的日期格式错误，正确格式为：yyyy-MM-dd HH:mm:ss",e);
        }
        int count = sysLogMapper.countBySearchDto(searchLogDto);
        if(count > 0){
            List<SysLogWithBLOBs> pageList = sysLogMapper.getPageListBySearchDto(searchLogDto, pageQuery);
            return PageResult.<SysLogWithBLOBs>builder().data(pageList).total(count).build();
        }

        return PageResult.<SysLogWithBLOBs>builder().build();

    }

    public void saveDeptLog(SysDept before, SysDept after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId():after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "":JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperaterTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    public void saveUserLog(SysUser before, SysUser after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId():after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "":JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperaterTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    public void saveAclModuleLog(SysAclModule before, SysAclModule after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId():after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "":JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperaterTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    public void saveAclLog(SysAcl before, SysAcl after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId():after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "":JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperaterTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleLog(SysRole before, SysRole after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId():after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "":JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperaterTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "":JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperaterTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "":JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperaterIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperaterTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }
}

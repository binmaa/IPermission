package com.ipermission.service;


import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.ipermission.beans.Mail;
import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.common.RequestHolder;
import com.ipermission.dao.SysUserMapper;
import com.ipermission.exception.ParamException;
import com.ipermission.model.SysUser;
import com.ipermission.param.UserParam;
import com.ipermission.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    public void save(UserParam userParam){
        BeanValidator.check(userParam);
        if(checkTelephoneExist(userParam.getTelephone(),userParam.getId())){
            throw new ParamException("该手机号已被占用");
        }
        if(checkExistMail(userParam.getMail(),userParam.getId())){
            throw new ParamException("该邮箱已被占用");
        }
        //获取密码
        String passWord = PasswordUtil.randomPassword();
        passWord = "123456";
        //发送邮件
        Mail mail = Mail.builder().subject("权限管理初始密码").message("您的权限管理系统初始密码为：" + passWord)
                .receivers(Sets.newHashSet(userParam.getMail())).build();
        boolean b = MailUtil.send(mail);
        if(!b){
            throw new ParamException("密码发送失败");
        }

        passWord = MD5Util.encrypt(passWord);
        SysUser after = SysUser.builder().username(userParam.getUsername()).telephone(userParam.getTelephone()).
                mail(userParam.getMail()).deptId(userParam.getDeptId()).status(userParam.getStatus()).
                remark(userParam.getRemark()).password(passWord).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperaterIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperaterTime(new Date());
        sysUserMapper.insert(after);
    }

    public void update(UserParam userParam){
        BeanValidator.check(userParam);
        if(checkTelephoneExist(userParam.getTelephone(),userParam.getId())){
            throw new ParamException("该手机号已被占用");
        }
        if(checkExistMail(userParam.getMail(),userParam.getId())){
            throw new ParamException("该邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(userParam.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");
        SysUser after = SysUser.builder().id(userParam.getId()).username(userParam.getUsername()).telephone(userParam.getTelephone()).
                mail(userParam.getMail()).deptId(userParam.getDeptId()).status(userParam.getStatus()).
                remark(userParam.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperaterIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperaterTime(new Date());//todo
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkTelephoneExist(String telephone,Integer userId){
        return sysUserMapper.countByTelephone(telephone,userId) > 0;
    }

    private boolean checkExistMail(String mail,Integer userId){
        return sysUserMapper.countByMail(mail,userId) > 0;
    }

    public SysUser findByKeyWord(String keyWord){
        return sysUserMapper.findByKeyWord(keyWord);
    }

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery){
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDeptId(deptId);
        if(count > 0){
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, pageQuery);;
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    public List<SysUser> getAll(){
        return sysUserMapper.getAll();
    }

}

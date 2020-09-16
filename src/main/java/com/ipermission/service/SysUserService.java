package com.ipermission.service;

import com.google.common.base.Preconditions;
import com.ipermission.beans.PageQuery;
import com.ipermission.beans.PageResult;
import com.ipermission.dao.SysUserMapper;
import com.ipermission.exception.ParamException;
import com.ipermission.model.SysDept;
import com.ipermission.model.SysUser;
import com.ipermission.param.DeptParam;
import com.ipermission.param.UserParam;
import com.ipermission.util.BeanValidator;
import com.ipermission.util.LevelUtils;
import com.ipermission.util.MD5Util;
import com.ipermission.util.PasswordUtil;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

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
        passWord = MD5Util.encrypt(passWord);
        //发送邮件
        //TODO

        SysUser after = SysUser.builder().username(userParam.getUsername()).telephone(userParam.getTelephone()).
                mail(userParam.getMail()).deptId(userParam.getDeptId()).status(userParam.getStatus()).
                remark(userParam.getRemark()).password(passWord).build();
        after.setOperator("system-save");//todo
        after.setOperaterIp("127.0.0.1");//todo
        after.setOperaterTime(new Date());//todo
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
        after.setOperator("system-update");//todo
        after.setOperaterIp("127.0.0.1");//todo
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
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, pageQuery);

            return PageResult.builder().total(count).data(sysUsers).build();
        }
        return PageResult.builder().build();
    }

}

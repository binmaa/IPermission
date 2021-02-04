package com.ipermission.service;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.ipermission.dao.SysAclMapper;
import com.ipermission.dao.SysAclModuleMapper;
import com.ipermission.dao.SysDeptMapper;
import com.ipermission.dto.AclDto;
import com.ipermission.dto.AclModuleLevelDto;
import com.ipermission.dto.DeptLevelDto;
import com.ipermission.model.SysAcl;
import com.ipermission.model.SysAclModule;
import com.ipermission.model.SysDept;
import com.ipermission.util.LevelUtils;
import com.sun.codemodel.internal.JForEach;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class SysTreeService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysCoreService sysCoreService;

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 用户的权限树
     * @param userId
     * @return
     */
    public List<AclModuleLevelDto> userAclTree(int userId){
        //用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        //Acl封装AclDto
        for(SysAcl sysAcl:userAclList){
            AclDto aclDto = AclDto.adapt(sysAcl);
            aclDto.setHasAcl(true);
            aclDto.setChecked(true);
            aclDtoList.add(aclDto);
        }
        return aclListToTree(aclDtoList);
    }

    /**
     * 权限树
     * @param roleId
     * @return
     */
    public List<AclModuleLevelDto> roleTree(int roleId){
        // 1、当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        //2、当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);

        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        //所有权限点
        List<SysAcl> allAclList = sysAclMapper.getAll();
        Set<SysAcl> aclSet = Sets.newHashSet(allAclList);
        //aclSet.addAll(userAclList);

        List<AclDto> aclDtoList = Lists.newArrayList();
        for(SysAcl acl:aclSet){
            AclDto dto = AclDto.adapt(acl);
            if(userAclIdSet.contains(dto.getId())){//是否有权限
                dto.setHasAcl(true);
            }
            if(roleAclIdSet.contains(dto.getId())){//权限是否选中
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    private List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList){
        if(CollectionUtils.isEmpty(aclDtoList)){
            return Lists.newArrayList();
        }
        //权限模块树
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        //权限模块权限集合
        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto aclDto:aclDtoList){
            if(aclDto.getStatus() == 1){
                moduleIdAclMap.put(aclDto.getAclModuleId(),aclDto);//封装成aclModuleId->List<SysAcl> 格式数据
            }
        }
        bindAclsWithOrder(aclModuleLevelList,moduleIdAclMap);
        return aclModuleLevelList;
    }

    /**
     * 权限模块树绑定权限
     * @param aclModuleLevelList
     * @param moduleIdAclMap
     */
    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList,Multimap<Integer,AclDto> moduleIdAclMap){
        if(CollectionUtils.isEmpty(aclModuleLevelList)){
            return;
        }
        for(AclModuleLevelDto aclModuleLevelDto:aclModuleLevelList){
            List<AclDto> aclDtos = (List<AclDto>)moduleIdAclMap.get(aclModuleLevelDto.getId());
            if(CollectionUtils.isNotEmpty(aclDtos)){
                Collections.sort(aclDtos,aclSeqComparator);
                aclModuleLevelDto.setAclList(aclDtos);
            }
            bindAclsWithOrder(aclModuleLevelDto.getAclModuleList(),moduleIdAclMap);
        }

    }

    public List<AclModuleLevelDto> aclModuleTree(){
        List<SysAclModule> allAclModule = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> aclModuleLevelDtos = Lists.newArrayList();
        for (SysAclModule sysAclModule:allAclModule){
            AclModuleLevelDto aclModuleAdapt = AclModuleLevelDto.adapt(sysAclModule);
            aclModuleLevelDtos.add(aclModuleAdapt);
        }
        return aclModuleToTree(aclModuleLevelDtos);
    }
    /**
     * 设置权限模块tree首层 构建层级权限模块集合levelAclModuleMap {level ->[adpt1,adpt2...]}
     * @param aclModuleLevelList 全部权限模块集合
     * @return
     */
    public List<AclModuleLevelDto> aclModuleToTree (List<AclModuleLevelDto> aclModuleLevelList){
        if(CollectionUtil.isEmpty(aclModuleLevelList)){
            return Lists.newArrayList();
        }
        //level ->[adpt1,adpt2...]
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();//权限模块树首层
        for(AclModuleLevelDto aclModuleLevelDto : aclModuleLevelList){
            levelAclModuleMap.put(aclModuleLevelDto.getLevel(),aclModuleLevelDto);
            if(LevelUtils.ROOT.equals(aclModuleLevelDto.getLevel())){
                rootList.add(aclModuleLevelDto);
            }
        }
        Collections.sort(rootList, aclModuleSeqComparator);
        transfromAclModuleTree(rootList,LevelUtils.ROOT,levelAclModuleMap);//递归构建权限模块树 从首层开始递归构建所有子层
        return rootList;
    }
    /**
     * 构建权限模块树 递归
     * @param aclModuleLevelList 当前层级权限模块集合
     * @param level 父层级
     * @param levelAclModuleMap 所有权限模块集合{level,aclModlueLevellist}
     */
    private void transfromAclModuleTree(List<AclModuleLevelDto> aclModuleLevelList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < aclModuleLevelList.size(); i++) {
            //遍历该层每个元素
            AclModuleLevelDto aclModuleLevelDto = aclModuleLevelList.get(i);
            //处理当前层级数据
            String nextLevel = LevelUtils.calculateLevel(level, aclModuleLevelDto.getId());
            List<AclModuleLevelDto> tempAclModlueList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempAclModlueList)){
                //排序
                Collections.sort(tempAclModlueList,aclModuleSeqComparator);
                //设置下一层权限模块
                aclModuleLevelDto.setAclModuleList(tempAclModlueList);
                //进行下一层处理
                transfromAclModuleTree(tempAclModlueList,nextLevel,levelAclModuleMap);
            }
        }
    }


    public List<DeptLevelDto> deptTree(){
        List<SysDept> allDept = sysDeptMapper.getAllDept();
        List<DeptLevelDto> deptLevelDtos = Lists.newArrayList();
        for(SysDept sysDept : allDept){
            DeptLevelDto deptLevelDto = DeptLevelDto.adapt(sysDept);
            deptLevelDtos.add(deptLevelDto);
        }
        return deptListToTree(deptLevelDtos);
    }

    /**
     * 设置部门tree首层 构建层级部门集合levelDeptMap {level ->[adpt1,adpt2...]}
     * @param deptLevelList 全部部门集合
     * @return
     */
    public List<DeptLevelDto> deptListToTree (List<DeptLevelDto> deptLevelList){
        if(CollectionUtil.isEmpty(deptLevelList)){
            return Lists.newArrayList();
        }
        //level ->[adpt1,adpt2...]
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();//部门树首层
        for(DeptLevelDto deptLevelDto : deptLevelList){
            levelDeptMap.put(deptLevelDto.getLevel(),deptLevelDto);
            if(LevelUtils.ROOT.equals(deptLevelDto.getLevel())){
                rootList.add(deptLevelDto);
            }
        }
        Collections.sort(rootList, deptSeqComparator);
        transfromDeptTree(rootList,LevelUtils.ROOT,levelDeptMap);//递归构建部门树 从首层开始递归构建所有子层
        return rootList;
    }

    /**
     * 构建部门树 递归
     * @param deptLevelList 当前层级部门集合
     * @param level 父层级
     * @param levelDeptMap 所有部门{level,deptLevellist}
     */
    private void transfromDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            //遍历该层每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            //处理当前层级数据
            String nextLevel = LevelUtils.calculateLevel(level, deptLevelDto.getId());
            List<DeptLevelDto> tempDeptlist = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempDeptlist)){
                //排序
                Collections.sort(tempDeptlist,deptSeqComparator);
                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptlist);
                //进行下一层处理
                transfromDeptTree(tempDeptlist,nextLevel,levelDeptMap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };
    public Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };
    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };
}

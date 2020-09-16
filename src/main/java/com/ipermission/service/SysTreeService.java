package com.ipermission.service;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.ipermission.dao.SysDeptMapper;
import com.ipermission.dto.DeptLevelDto;
import com.ipermission.model.SysDept;
import com.ipermission.util.LevelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysTreeService {
    @Resource
    private SysDeptMapper sysDeptMapper;

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
}
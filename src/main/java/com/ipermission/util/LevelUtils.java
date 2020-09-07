package com.ipermission.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 部门层级工具类
 */
public class LevelUtils {
    private final static String SEPARATOR = ".";
    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel,int parentId){
        if(StringUtils.isBlank(parentLevel)){
            return ROOT;
        }else{
            return StringUtils.join(parentLevel,SEPARATOR,parentId);
        }
    }
}

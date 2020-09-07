package com.ipermission.Algorithm;

public class LongestCommonPrefix {
    /**
     * 最长公共前缀
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if(strs.length==0){
            return "";
        }
        String defaultPre = strs[0];
        for(int i = 0;i < strs.length;i++){
            if(defaultPre == ""){
                break;
            }
            while(!strs[i].startsWith(defaultPre)){
                defaultPre = defaultPre.substring(0,defaultPre.length()-1);
            }
        }
        return defaultPre;

    }

    public static void main(String[] args) {
        String[] strs = {"dog","racecar","car"};
        System.out.println(longestCommonPrefix(strs));
    }
}

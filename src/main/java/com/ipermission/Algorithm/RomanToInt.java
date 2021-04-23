package com.ipermission.Algorithm;



import java.util.HashMap;
import java.util.Map;

public class RomanToInt {
    public static void main(String[] args) {
        String s="IV";
        System.out.println(romanToInt(s));
    }
    public int getValue(char c){
        switch(c){
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
    public static int romanToInt(String s) {
        if(s == null){
            return 0;
        }
        Map<Character,Integer> mapping = new HashMap<>();
        mapping.put('I',1);
        mapping.put('V',5);
        mapping.put('X',10);
        mapping.put('L',50);
        mapping.put('C',100);
        mapping.put('D',500);
        mapping.put('M',1000);
        char[] chars = s.toCharArray();
        int sum = 0;
        for(int i=0 ;i<chars.length-1 ;i++){
            int currentValue = mapping.get(chars[i]);
            sum += currentValue<mapping.get(chars[i+1]) ? (currentValue*-1) : currentValue;
        }
        return sum + mapping.get(chars[chars.length-1]);

    }
    public static int romanToInt2(String s) {
        if(s == null){
            return 0;
        }
        Map<Character,Integer> mapping = new HashMap<>();
        mapping.put('I',1);
        mapping.put('V',5);
        mapping.put('X',10);
        mapping.put('L',50);
        mapping.put('C',100);
        mapping.put('D',500);
        mapping.put('M',1000);
        char[] chars = s.toCharArray();
        int sum = 0;
        for(int i=0 ;i<chars.length ;i++){
            char currentChar = chars[i];
            if(currentChar == 'I' && i+1 < chars.length && chars[i+1] == 'V'){
                sum +=4;
                i++;
                continue;
            }else if(currentChar == 'I' && i+1 < chars.length &&  chars[i+1] == 'X'){
                sum +=9;
                i++;
                continue;
            }else if(currentChar == 'X' && i+1 < chars.length && chars[i+1] == 'L'){
                sum +=40;
                i++;
                continue;
            }else if(currentChar == 'X' && i+1 < chars.length && chars[i+1] == 'C'){
                sum +=90;
                i++;
                continue;
            }else if(currentChar == 'C' && i+1 < chars.length && chars[i+1] == 'D'){
                sum +=400;
                i++;
                continue;
            }else if(currentChar == 'C' && i+1 < chars.length && chars[i+1] == 'M'){
                sum +=900;
                i++;
                continue;
            }
            sum +=mapping.get(chars[i]);
        }
        return sum;
    }
}

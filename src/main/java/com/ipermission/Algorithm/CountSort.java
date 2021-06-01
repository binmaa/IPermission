package com.ipermission.Algorithm;


import java.util.Arrays;

public class CountSort implements Sort{

    @Override
    public void sort(int[] sortData) {
        int maxElement = sortData[0];
        for (int data:sortData) {
            if(data > maxElement){
                maxElement = data;
            }
        }
        int[] tmpData = new int[maxElement+1];
        for (int data:sortData) {
            tmpData[data] = tmpData[data]+1;
        }
        int index = 0;
        for (int i = 0; i < tmpData.length; i++) {
            for (int j = 0; j < tmpData[i]; j++) {
                sortData[index] = i;
                index ++;
            }
        }
    }

    public static void main(String[] args) {
        int[] sortData = {32,23,5,34,87,92,14,76,0,3,56,23};
        Sort sort = new CountSort();
        System.out.println("sortData before = "+ Arrays.toString(sortData));
        sort.sort(sortData);
        System.out.println("sortData after = "+Arrays.toString(sortData));
    }
}

package com.ipermission.Algorithm;

import java.util.Arrays;

public class BubbleSort implements Sort{

    @Override
    public void sort(int[] sortData) {
        for (int i = 0; i < sortData.length; i++) {
            for (int j = i; j < sortData.length; j++) {
                if(sortData[i] > sortData[j]){
                    swap(sortData,i,j);
                }
            }
        }

    }

    private void swap(int[] sortData, int i, int j) {
        int tmp = sortData[i];
        sortData[i] = sortData[j];
        sortData[j] = tmp;
    }

    public static void main(String[] args) {
        int[] sortData = {32,23,5,34,87,92,14,76,0,3,56};
        Sort sort = new BubbleSort();
        System.out.println("sortData before = "+ Arrays.toString(sortData));
        sort.sort(sortData);
        System.out.println("sortData after = "+Arrays.toString(sortData));
    }
}

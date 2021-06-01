package com.ipermission.Algorithm;

import java.util.Arrays;

public class InsertSort implements Sort{
    @Override
    public void sort(int[] sortData) {
        for (int i = 1; i < sortData.length; i++) {
            int tmp = sortData[i];
            int point = i;
            while (point > 0 && sortData[point-1] > tmp){
                sortData[point] = sortData[point-1];
                point --;
            }
            sortData[point] = tmp;
            System.out.println("sortData = " + Arrays.toString(sortData));
        }
    }

    private void swap(int[] sortData, int i, int j) {
        int tmp = sortData[i];
        sortData[i] = sortData[j];
        sortData[j] = tmp;
    }

    public static void main(String[] args) {
        int[] sortData = {32,23,5,34,87,92,14,76,0,3,56,23};
        Sort sort = new InsertSort();
        System.out.println("sortData before = "+ Arrays.toString(sortData));
        sort.sort(sortData);
        System.out.println("sortData after = "+Arrays.toString(sortData));
    }
}

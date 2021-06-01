package com.ipermission.Algorithm;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] sort = {22,13,5,22,45,75,56,3,0};//
        System.out.println("sort = " + Arrays.toString(sort));
        int[] sortAfter = QuickSort.sort(sort);
        System.out.println(Arrays.toString(sortAfter));

    }

    public static int[] sort(int[] data){
        int[] sortData = Arrays.copyOf(data,data.length);
        quickSort(sortData,0,sortData.length-1);
        return sortData;
    }

    /**
     * 快排
     * @param sortData 待排对象
     * @param left 左边界
     * @param right 右边界
     */
    private static void quickSort(int[] sortData,int left,int right) {
        if(left < right){
            System.out.println("sortData = " + Arrays.toString(sortData));
            //int partition = partition(sortData,left,right);//获取下次分割位置
            int partition = partition2(sortData,left,right);//获取下次分割位置
            quickSort(sortData,left,partition - 1);
            quickSort(sortData,partition,right);
        }
    }

    private static int partition2(int[] sortData, int left, int right) {
        int compareIndex = left;
        int leftPoint = left+1;
        int rightPoint = right;
        while (leftPoint < rightPoint){
            while (leftPoint < rightPoint && sortData[leftPoint] <= sortData[compareIndex]){
                leftPoint ++;
            }
            while (leftPoint < rightPoint &&  sortData[rightPoint] >= sortData[compareIndex]){
                rightPoint --;
            }
            if(leftPoint < rightPoint){
                swap(sortData,leftPoint,rightPoint);
            }
        }
        swap(sortData,compareIndex,leftPoint-1);
        return leftPoint;
    }

    private static int partition(int[] sortData, int left, int right) {
        int compareIndex = left;//以首个位置为比较对象
        int leftPoint = left + 1;
        int rightPoint = right;
        while (leftPoint <= rightPoint){
            if(sortData[leftPoint] >= sortData[compareIndex] && sortData[rightPoint] < sortData[compareIndex]){
                swap(sortData,leftPoint,rightPoint);
            }
            if(sortData[leftPoint] < sortData[compareIndex]){
                leftPoint ++;
            }

            if(sortData[rightPoint] >= sortData[compareIndex]){
                rightPoint --;
            }

        }
        //交换首个位置和中间位置
        swap(sortData,leftPoint-1,compareIndex);
        return leftPoint;
    }

    private static void swap(int[] sortData, int leftPoint, int rightPoint) {
        int tmp = sortData[leftPoint];
        sortData[leftPoint] = sortData[rightPoint];
        sortData[rightPoint] = tmp;
    }


}

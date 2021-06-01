package com.ipermission.Algorithm;

import com.google.common.collect.Lists;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BucketSort implements Sort{
    @Override
    public void sort(int[] sortData) {
        int maxElement = sortData[0];
        int minElement = sortData[0];
        for (int data : sortData) {
            if(data > maxElement)
                maxElement = data;
            if(data < minElement)
                minElement = data;
        }
        int bucketNum = (maxElement - minElement) / sortData.length + 1;
        List<ArrayList<Integer>> bucket = new ArrayList<ArrayList<Integer>>();
        for (int i=0 ; i<bucketNum ; i++){
            bucket.add(Lists.newArrayList());
        }
        for (int data:sortData) {
            int bucketIndex = (data - minElement) / sortData.length;
            bucket.get(bucketIndex).add(data);
        }
        Sort countSort = new CountSort();
        for (int i = 0; i < bucket.size(); i++) {
            Collections.sort(bucket.get(i));
        }
        int sortIndex = 0;
        for (int i = 0; i < bucket.size(); i++) {
            for (int j = 0; j < bucket.get(i).size(); j++) {
                sortData[sortIndex] = bucket.get(i).get(j);
                sortIndex ++;
            }
            System.out.println("bucket " +i + " = " +  Arrays.toString(bucket.get(i).toArray()));
        }
        //System.out.println("sortData = " + Arrays.toString(sortData));
    }
    public static void main(String[] args) {
        int[] sortData = {32,23,5,34,87,92,14,76,0,3,56,23,23};
        Sort sort = new BucketSort();
        System.out.println("sortData before = "+ Arrays.toString(sortData));
        sort.sort(sortData);
        System.out.println("sortData after = "+Arrays.toString(sortData));
    }
}

package com.ipermission.datastructure.trie;

import com.ipermission.datastructure.tree.BstSet;
import com.ipermission.datastructure.tree.FileOperation;
import com.ipermission.datastructure.tree.Set;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        //BstSet<String> bstSet = new BstSet<>();
        //testSet("pride-and-prejudice.txt",bstSet);
        Trie trie = new Trie();
        testTrie("pride-and-prejudice.txt",trie);
        System.out.println(trie.contain("whatsoever"));
        System.out.println(trie.match("whatsoe..r"));
        System.out.println(trie.match("augus."));
    }

    public static void testTrie(String fileName, Trie trie){
        long startTime = System.nanoTime();
        ArrayList<String> keyWords = new ArrayList<>();
        if(FileOperation.readFile(fileName,keyWords)){
            for (String keyWord:keyWords) {
                trie.add(keyWord);
            }
            for (String keyWord:keyWords) {
                trie.contain(keyWord);
            }
            System.out.println(fileName+" words single num："+trie.size());
            //System.out.println(fileName+" words single num："+keyWords.size());
        }
        long endTime = System.nanoTime();
        System.out.println("Trie 使用时间："+(endTime-startTime)/1000000000d);
    }

    public static void testSet(String fileName, Set set){
        long startTime = System.nanoTime();
        ArrayList<String> keyWords = new ArrayList<>();
        if(FileOperation.readFile(fileName,keyWords)){
            for (String keyWord:keyWords) {
                set.add(keyWord);
            }
            for (String keyWord:keyWords) {
                set.contains(keyWord);
            }
            System.out.println(fileName+" words single num："+set.size());
        }
        long endTime = System.nanoTime();
        System.out.println("BstSet 使用时间："+(endTime-startTime)/1000000000d);
    }


}

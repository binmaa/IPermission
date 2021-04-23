package com.ipermission.Algorithm;

import java.util.TreeMap;

/**
 * 字典树
 */
public class Trie208 {
    private Node root;
    private int size;

    public Trie208() {
        this.root = new Node(false);
        this.size = 0;
    }

    /**
     * 添加词
     * @param word
     */
    public void insert(String word){
        Node cur = root;
        for (int i=0 ; i<word.length() ; i++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){//不存在此节点
                cur.next.put(c,new Node());//添加节点
            }
            cur = cur.next.get(c);//cur 指向节点
        }
        if(!cur.isWord){//循环结束 设置是否新字符
            cur.isWord = true;
            size ++;
        }
    }


    /**
     * 是否存在以prefix为前缀的字符串
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix){
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }

    /**
     * 是否包含单词
     * @param word
     * @return
     */
    public boolean search(String word){
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }else{
                cur = cur.next.get(c);
            }
        }
        return cur.isWord;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 节点内部类
     */
    private class Node{
        private boolean isWord;
        private TreeMap<Character,Node> next;

        private Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }
        private Node(){
            this(false);
        }
    }
}

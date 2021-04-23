package com.ipermission.Algorithm;


import java.util.Set;
import java.util.TreeMap;

/**
 * 字典树
 */
public class WordDictionary {
    private Node root;
    private int size;

    public WordDictionary() {
        this.root = new Node(false);
        this.size = 0;
    }

    /**
     * 匹配查询
     * @param reg [.]匹配任意字符
     * @return
     */
    public boolean search(String reg){
        return match(root,reg,0);
    }

    /**
     * 匹配查询递归
     * @param node
     * @param reg
     * @param index
     * @return
     */
    private boolean match(Node node,String reg,int index){
        if(reg.length() == index){
            return node.isWord;
        }
        char c = reg.charAt(index);
        if(c == '.'){//任意匹配字符
            Set<Character> keySet = node.next.keySet();
//            for (char ch:keySet) {
//                return  match(node.next.get(ch),reg,index+1); //真假都返回
//            }
            for (char ch:keySet) {
                if(match(node.next.get(ch),reg,index+1)){//存在就是真 个别不存在不返回
                    return true;
                }
            }
            return false;
        }else{
            if(node.next.get(c) == null)
                return false;
            return match(node.next.get(c),reg,index+1);
        }
    }

    /**
     * 添加词
     * @param word
     */
    public void addWord(String word){
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
    public boolean contain(String word){
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

    public static void main(String[] args) {
        //"\"WordDictionary\",\"addWord\",\"addWord\",\"addWord\",\"addWord\",\"search\",\"search\",\"addWord\",\"search\",\"search\",\"search\",\"search\",\"search\",\"search\"]\n" + "[[],[\"at\"],[\"and\"],[\"an\"],[\"add\"],[\"a\"],[\".at\"],[\"bat\"],[\".at\"],[\"an.\"],[\"a.d.\"],[\"b.\"],[\"a.d\"],[\".\"]]";
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("search");
        wordDictionary.addWord("search");
        System.out.println(wordDictionary.search("search"));
    }
}

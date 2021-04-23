package com.ipermission.Algorithm;
import java.util.TreeMap;

class MapSum {
    private int size;
    private Node root;

    public MapSum() {
        this.size = 0;
        this.root = new Node(0);
    }

    /**
     * 插入数据
     * @param key
     * @param val
     */
    public void insert(String key, int val) {
        Node cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(cur.next.get(c) == null){//没有当前节点 添加
                cur.next.put(c,new Node(0));
            }
            cur = cur.next.get(c);
        }
        cur.val = val;
    }

    public int sum(String prefix) {
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {//遍历前缀
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return 0;
            }
            cur = cur.next.get(c);
        }
        return sumChild(cur);
    }
    private int sumChild(Node node){
        int res = node.val;
        for (char c : node.next.keySet()) {
            res += sumChild(node.next.get(c));
        }
        return res;
    }

    private class Node{
        private int val;
        private TreeMap<Character,Node> next;

        public Node(int val) {
            this.val = val;
            this.next = new TreeMap();
        }
    }
}

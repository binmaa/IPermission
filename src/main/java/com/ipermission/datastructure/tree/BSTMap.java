package com.ipermission.datastructure.tree;

/**
 *
 * @param <K>
 * @param <V>
 */
public class BSTMap <K extends Comparable<K>,V> implements Map<K,V>{

    private class Node{
        private K key;
        private V value;
        private Node left,right;

        public Node(K key,V value){
            this.key = key;
            this.value = value;
            this .left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    @Override
    public void add(K key, V value) {
        root = add(root,key,value);
    }

    private Node add(Node node,K key, V value){
        if(node == null){//递归到空叶子节点 添加节点 size+1
            size ++;
            return node = new Node(key,value);
        }
        if(key.compareTo(node.key) < 0){//key < node.key 在当前节点左侧 进入左子树
            node.left = add(node.left,key,value);
        }else if(key.compareTo(node.key) > 0){
            node.right = add(node.right,key,value);
        }else{
            node.value = value;//key == node.key 当前节点就是插入的节点
        }
        return node;
    }

    @Override
    public V remove(K key) {
        V v = this.get(key);
        root = remove(root,key);
        return v;
    }

    /**
     * 移除node的最小节点
     * @param node
     * @return
     */
    public Node removeMinNode(Node node){
        if(node.left == null){//左节点为null 该节点为待移除节点
            Node rightNode = node.right;//暂存右子树
            node.right = null;//清空右节点引用
            size -- ;
            return rightNode;//返回node右子树
        }
        node.left = this.removeMinNode(node.left);
        return node;
    }

    /**
     * 查找后继节点
     * @param node
     * @return
     */
    public Node minNode(Node node){
        while (node.left != null){//左子树为null
            node = node.left;
        }
        return node;
    }

    /**
     * 移除键值为k的节点
     * @param node
     * @param key
     * @return
     */
    private Node remove(Node node, K key) {
        if(node == null){
            return null;
        }
        if(key.compareTo(node.key) < 0){//在左子树
            return remove(node.left,key);
        }else if(key.compareTo(node.key) > 0){//在右子树
            return remove(node.right,key);
        }else{//当前节点
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;//移除node对node.left的占用
                size --;
                return leftNode;
            }else if(node.left == null){
                size --;
                return node.right;
            }else{//移除方式：用比当前节点大的最小元素(后继)替换
                Node successorNode = this.minNode(node.right);
                successorNode.left = node.left;
                successorNode.right = this.removeMinNode(node.right);
                //清除node引用
                node.left = node.right = null;
                return successorNode;
            }
        }
    }

    @Override
    public boolean contains(K key) {
        return this.get(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = this.getNode(key);
        return node == null ? null:node.value;
    }

    /**
     * 获取键为key的节点
     * @param key
     * @return
     */
    private Node getNode(K key){
        if(root == null){
            new IllegalArgumentException("root is empty");
        }
        Node cur = root;
        while(true){
            if(cur == null){
                return null;
            }
            if(key.compareTo(cur.key) < 0){//获取元素在左子树
                cur = cur.left;
            }else if(key.compareTo(cur.key) > 0){//获取元素在右子树
                cur = cur.right;
            }else{
                return cur;
            }
        }
    }

    @Override
    public void set(K key, V newValue) {
        Node node = this.getNode(key);
        if(node == null){
            new IllegalArgumentException(key + "doesn't exist");
        }
        node.value = newValue;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
}

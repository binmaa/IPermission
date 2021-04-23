package com.ipermission.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<E extends Comparable> {

    private Node root;
    private int size;

    public BST(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 是否包含元素
     * @param e
     * @return
     */
    public boolean contains(E e){
        return contains(root,e);
    }
    private boolean contains(Node node,E e){
        if(node == null){
            return false;
        }
        if(e.compareTo(node.e) == 0){
            return true;
        }else if(e.compareTo(node.e) < 0){//e比node.e小去左子树查找
            return contains(node.leftNode,e);
        }else{//e比node.e大去右子树查找
            return contains(node.rightNode,e);
        }
    }

    /**
     * 添加节点
     * @param e
     */
    public void add(E e){
         root = add(root,e);
    }

    /**
     * 移除元素
     *
     * 思路：移除元素的最小后继替代该元素
     * @param e
     */
    public void removeElement(E e){
        if(root == null){
            return;
        }
        root = removeElement(root,e);
    }

    /**
     * 移除元素递归
     * @param node
     * @param e
     */
    private Node removeElement(Node node,E e){
        if(e.compareTo(node.e) > 0){//e > node.e 移除的元素在node右侧
            node.rightNode = removeElement(node.rightNode,e);
            return node;
        }else if(e.compareTo(node.e) < 0){
            node.leftNode = removeElement(node.leftNode,e);
            return node;
        }else{//查找到待移除的元素
            if(node.leftNode == null){//移除元素左节点为null
                Node rightNode = node.rightNode;
                //node.rightNode = null; //移除引用
                size -- ;
                return rightNode;
            }
            if(node.rightNode == null){//移除元素右节点为null
                Node leftNode = node.leftNode;
                //node.leftNode = null; //移除引用
                size --;
                return leftNode;
            }

            Node successor = minElement(node);//查找后续元素
            removeMinElement(node);//移除后继元素
            successor.leftNode = node.leftNode;
            successor.rightNode = node.rightNode;
            //node.rightNode = node.leftNode = null;
            return successor;
        }

    }

    public E removeMaxElement(){
        if(size == 0){
            throw new IllegalArgumentException("BST is Empty");
        }
        E ret = maxElement();
        root = removeMaxElement(root);
        return ret;
    }

    /**
     * 移除最大元素递归
     * @param node
     * @return
     */
    private Node removeMaxElement(Node node){
        if(node.rightNode == null){
            Node leftNode = node.leftNode;
            //node.leftNode = null;//将无用节点回收
            size--;
            return leftNode;
        }
        node.rightNode = removeMaxElement(node.rightNode);
        return node;
    }

    /**
     * 从二叉树中移除最小元素
     * @return
     */
    public E removeMinElement(){
        if(size == 0){
            throw new IllegalArgumentException("BST is Empty");
        }
        E ret = minElement();
        root = removeMinElement(root);
        return ret;
    }

    /**
     * 移除最小元素 递归
     * @param node
     * @return
     */
    private Node removeMinElement(Node node){
        if(node.leftNode == null){
            Node rightNode = node.rightNode;
            //node.leftNode = rightNode;
            size--;
            return rightNode;
        }
        node.leftNode = removeMinElement(node.leftNode);
        return node;
    }

    /**
     * 获取树中最大元素
     * @return
     */
    public E maxElement(){
        if(size == 0){
            throw new IllegalArgumentException("BST is Empty");
        }
        Node maxNode = maxElement(root);
        return (E) maxNode.e;
    }
    /**
     * 获取最大元素
     * @param node
     * @return
     */
    private Node maxElement(Node node){
        if(node.rightNode == null){
            return node;
        }
        return maxElement(node.rightNode);
    }

    /**
     * 查找最小元素
     * @return
     */
    public E minElement(){
        if(size == 0){
            throw new IllegalArgumentException("BST is Empty");
        }
        Node minNode = minElement(root);
        return (E) minNode.e;
    }

    /**
     * 获取最小元素
     * @param node
     * @return
     */
    private Node minElement(Node node){
        if(node.leftNode == null){
            return node;
        }
        return minElement(node.leftNode);
    }

    /**
     * 添加节点递归函数
     * @param node
     * @param e
     * @return
     */
    private Node add(Node node,E e){
        if(node == null){
            size ++;
            return new Node(e);
        }
        if(e.compareTo(node.e) < 0){//插入左子树
            node.leftNode = add(node.leftNode,e);
        }else if(e.compareTo(node.e) > 0){//插入右子树
            node.rightNode = add(node.rightNode,e);
        }
        return node;
    }

    /**
     * 层序遍历
     */
    public void levelOrder(){
        if(root == null){
            return;
        }
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node curNode = q.remove();
            System.out.println(curNode.e);
            if(curNode.leftNode != null){
                q.add(curNode.leftNode);
            }
            if(curNode.rightNode != null){
                q.add(curNode.rightNode);
            }
        }
    }

    /**
     * 后序遍历非递归
     */
    public void postOrderNr(){
        if(size == 0){
            return;
        }

    }

    /**
     * 前序遍历 非递归
     */
    public void preOrderNr(){
        if(root == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node curNode = stack.pop();
            System.out.println(curNode.e);
            if(curNode.rightNode != null){
                stack.push(curNode.rightNode);
            }
            if(curNode.leftNode != null){
                stack.push(curNode.leftNode);
            }
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder(){
        inOrder(root);
    }

    /**
     * 中序遍历递归
     * @param node
     */
    private void inOrder(Node node){
        if(node == null)
            return;
        inOrder(node.leftNode);
        System.out.println(node.e);
        inOrder(node.rightNode);
    }

    /**
     * 后序遍历
     */
    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node node){
        if(node == null){
            return;
        }
        postOrder(node.leftNode);
        postOrder(node.rightNode);
        System.out.println(node.e);
    }

    /**
     * 前序遍历
     */
    public void preOrder(){
        preOrder(root);
    }

    /**
     * 前序遍历递归
     * @param node
     */
    private void preOrder(Node node){
        if(node == null){
            return;
        }
        System.out.println(node.e);
        preOrder(node.leftNode);
        preOrder(node.rightNode);
    }

    /**
     * 节点内部类
     * @param <E>
     */
    private class Node<E>{
        private E e;//节点信息
        private Node leftNode,rightNode;//左右子树
        public Node(E e){
            this.e = e;
            leftNode = null;
            rightNode = null;
        }
    }
    @Override
    public String toString(){
        StringBuilder bstBuffer = new StringBuilder();
        generateBSTString(root,0,bstBuffer);
        return bstBuffer.toString();
    }
    private void generateBSTString(Node node,int depth,StringBuilder builder){
        if(node == null) {//递归结束条件
            return;
        }
        builder.append(generateDepthString(depth)).append(node.e).append("\n");
        depth ++;
        generateBSTString(node.leftNode,depth,builder);
        generateBSTString(node.rightNode,depth,builder);
    }

    /**
     * 拼接深度字符串
     * @param depth
     * @return
     */
    private String generateDepthString(int depth){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            builder.append("---");
        }
        return builder.toString();
    }



    public void addOld(E e){
        if(root == null){
            size ++;
            root = new Node(e);
            return;
        }
        addOld(root,e);
    }

    public void addOld(Node node,E e){
        if(node.e == e){//与任意节点相等不处理
            return;
        }
        if(e.compareTo(node.e) < 0 && node.leftNode == null){//node的左子树为空 且e<node.lefeNode
            size ++;
            node.leftNode = new Node(e);
        }else if(e.compareTo(node.e) > 0 && node.rightNode == null){
            size ++;
            node.rightNode = new Node(e);
        }
        if(e.compareTo(node.e) < 0){
            addOld(node.leftNode,e);
        }else{
            addOld(node.rightNode,e);
        }
    }
}

package com.ipermission.datastructure.tree;

/**
 * 自定义set
 * @param <E>
 */
public class BstSet<E extends Comparable<E>> implements Set<E>  {

    private BST<E> bst;
    public BstSet(){
        bst = new BST<>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public int size() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    @Override
    public void remove(E e) {
        bst.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }
}

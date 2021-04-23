package com.ipermission.datastructure.tree;

public interface Set<E> {
    public void add(E e);
    public int size();
    public boolean isEmpty();
    public void remove(E e);
    public boolean contains(E e);
}

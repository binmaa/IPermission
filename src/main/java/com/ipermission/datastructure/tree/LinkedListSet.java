package com.ipermission.datastructure.tree;

public class LinkedListSet<E extends Comparable<E>> implements Set<E> {

    private LinkedList<E> linkedList;

    public LinkedListSet(){
        linkedList = new LinkedList<E>();
    }

    @Override
    public void add(E e) {
        if(!linkedList.contains(e)){
            linkedList.addLast(e);
        }
    }

    @Override
    public int size() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public void remove(E e) {
        linkedList.removeLast();

    }

    @Override
    public boolean contains(E e) {
        return linkedList.contains(e);
    }
}

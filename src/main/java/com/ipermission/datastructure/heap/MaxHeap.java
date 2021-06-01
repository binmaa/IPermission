package com.ipermission.datastructure.heap;

public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;
    public MaxHeap(){
        data = new Array<E>();
    }

    public MaxHeap(int capacity){
        data = new Array<E>(capacity);
    }

    /**
     *
     * @return
     */
    public int size(){
        return data.getSize();
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }

    public void add(){}

    private int parent(int index){
        return index;
    }

}

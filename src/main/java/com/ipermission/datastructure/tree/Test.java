package com.ipermission.datastructure.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        //test4();
        //test5();
        Map<String, Integer> map = new BSTMap<>();
        testBstMap(map,"pride-and-prejudice.txt");
        test6();
    }

    public static void testBstMap(Map<String,Integer> map,String fileName){
        long startTime = System.nanoTime();
        ArrayList<String> keyWords = new ArrayList<>();
        if(FileOperation.readFile(fileName,keyWords)){
            System.out.println(fileName + " words num："+keyWords.size());
            for (String word:keyWords) {
                if(map.contains(word)){
                    map.add(word,map.get(word)+1);
                }else{
                    map.add(word,1);
                }
            }
        }
        System.out.println("Total different words: " + map.getSize());
        System.out.println("Frequency of PRIDE: " + map.get("pride"));
        System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        long endTime = System.nanoTime();
        System.out.println("使用时间："+(endTime-startTime)/1000000000d);

    }

    /**
     * compare BsrSet and LinkedListSet
     */
    public static void test6(){
        BstSet<String> bstSet = new BstSet<>();
        TestSet(bstSet,"pride-and-prejudice.txt");
        System.out.println();
        LinkedListSet<String> linkedListSet = new LinkedListSet<>();
        TestSet(linkedListSet,"a-tale-of-two-cities.txt");
    }

    public static void TestSet(Set<String> set,String fileName){
        long startTime = System.nanoTime();
        ArrayList<String> keyWords = new ArrayList<>();
        if(FileOperation.readFile(fileName,keyWords)){
            System.out.println(fileName + " words num："+keyWords.size());
            for (String keyWord:keyWords) {
                set.add(keyWord);
            }
            System.out.println(fileName+" words single num："+set.size());
        }
        long endTime = System.nanoTime();
        System.out.println("使用时间："+(endTime-startTime)/1000000000d);
    }

    /**
     * BstSet
     */
    public static void test5(){
        ArrayList<String> keyWords = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt",keyWords)){
            System.out.println("pride-and-prejudice keys num："+keyWords.size());
            Set<String> bstSet = new BstSet<>();
            for (String keyWord:keyWords) {
                bstSet.add(keyWord);
            }
            System.out.println("pride-and-prejudice keys single num："+bstSet.size());
        }

        System.out.println();
        keyWords.clear();
        if(FileOperation.readFile("a-tale-of-two-cities.txt",keyWords)){
            System.out.println("a-tale-of-two-cities keys num："+keyWords.size());
            Set<String> bstSet = new BstSet<>();
            for (String keyWord:keyWords) {
                bstSet.add(keyWord);
            }
            System.out.println("a-tale-of-two-cities keys single num："+bstSet.size());
        }

    }
    public static void test4(){
        BST<Integer> bst = new BST<>();
        List param = new ArrayList<>();
        Random random = new Random();
        //int[] params = {34,23,46,40,1,26,40,55};
        int[] params = {34,23,46,1,26,55};
        for (int i = 0; i < params.length; i++) {
            bst.add(params[i]);
        }
        bst.removeElement(55);
        System.out.println(bst);
    }
    public static void test3(){
        BST<Integer> bst = new BST<>();
        List param = new ArrayList<>();
        Random random = new Random();
        int[] params = {34,23,46,40};
        for (int i = 0; i < params.length; i++) {
            bst.add(params[i]);
        }
        System.out.println(bst.maxElement()+"   长度"+bst.size());

        List<Integer> removeList = new ArrayList<>();
        while(!bst.isEmpty()){
            removeList.add(bst.removeMaxElement());
        }
        /*int bstSize = bst.size();
        for (int i = 0; i < bstSize; i++) {
            removeList.add(bst.removeMax());
        }*/
        System.out.println("长度"+removeList.size() + removeList);
        System.out.println(bst);

        boolean check = true;
        for (int i = 1; i < removeList.size(); i++) {
            if(removeList.get(i-1) < removeList.get(i))
                check = false;
        }
        System.out.println("从二叉树中移除最大元素"+check);
    }
    public static void test2(){
        BST<Integer> bst = new BST<>();
        List param = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            bst.add(random.nextInt(1000));
        }
        System.out.println(bst.minElement()+"   长度"+bst.size());
        List<Integer> removeList = new ArrayList<>();
        while (!bst.isEmpty()){
            removeList.add(bst.removeMinElement());
        }

        System.out.println("长度"+removeList.size() +removeList);

        boolean check = true;
        for (int i = 1; i < removeList.size(); i++) {
                if(removeList.get(i-1) > removeList.get(i))
                    check = false;
        }
        System.out.println("从二叉树中移除最小元素"+check);
    }

    public static void test1(){
        int[] params = {34,34,23,1,26,46,0,22,55,4,89};
        BST<Integer> bst = new BST<>();
        for (int param:params) {
            bst.addOld(param);
        }
        //                      34
        //                     /  \
        //                    23  46
        //                   /  \   \
        //                  1    26  55
        //                 / \         \
        //                0  22         89
        //                   /
        //                  4
        System.out.println("最小元素---------------------");
        System.out.println(bst.minElement());
        System.out.println("层序---------------------");
        bst.levelOrder();
        System.out.println("前序---------------------");
        bst.preOrder();
        System.out.println("前序非递归---------------------");
        bst.preOrderNr();
        System.out.println("中序---------------------");
        bst.inOrder();
        System.out.println("后序---------------------");
        bst.postOrder();
        System.out.println("---------------------");
        System.out.println(bst.toString());
        System.out.println(bst.contains(2));
    }
}

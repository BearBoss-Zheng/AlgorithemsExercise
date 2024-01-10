package test;

import day01.HeapSort;

import java.util.*;

/**
 * @author zjx
 * @Date 2023-12-27 00:24:12
 * @Desc
 */
public class Test {
    public static void main(String[] args) {

        Queue queue = new LinkedList();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());



    }
}

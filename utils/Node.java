package utils;

import day03.BinaryTree;

/**
 * @author zjx
 * @Date 2024-01-10 20:15:11
 * @Desc
 */
public class Node<V> {
    public V value;
    public Node<V> left;
    public Node<V> right;
    public Node<V> next;

    public Node(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{Value = "+value+"}";
    }
}

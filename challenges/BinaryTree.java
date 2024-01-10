package challenges;

/**
 * @author zjx
 * @Date 2024-01-10 19:16:20
 * @Desc:
 * 1. 非递归两种方式实现二叉树的先序、中序、后序遍历
 * 2. 打印二叉树，如下所示：
 *       7
 *    3
 *       6
 * 1
 *       5
 *    2
 *       4
 *
 */
public class BinaryTree {

    static class Node<V>{
        V value;
        Node<V> left;
        Node<V> right;

        public Node(V v) {
            this.value = v;
        }

        @Override
        public String toString() {
            return "{Value = "+value+"}";
        }
    }
}

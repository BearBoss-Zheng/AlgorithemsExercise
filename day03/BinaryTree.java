package day03;

import utils.Node;

import java.util.*;

/**
 * @author zjx
 * @Date 2024-01-09 22:43:16
 * @Desc:
 * 二叉树问题
 * 1.用递归和非递归两种方式实现二叉树的先序、中序、后序遍历
 *
 * 如何直观的打印一颗二叉树
 * 如何完成二叉树的宽度优先遍历（常见题目：求一颗二叉树的宽度）
 */
public class BinaryTree {
    public static void main(String[] args) {
        Node<Integer> root = new Node<>(1);
        root.left = new Node<>(2);
        root.right = new Node<>(3);
        root.left.left = new Node<>(4);
        root.left.right = new Node<>(5);
        root.right.left = new Node<>(6);
        root.left.left.right = new Node<>(7);

        System.out.println("递归方式前序遍历");
        recursivePreorderTraversal(root);
        System.out.println("非递归方式前序遍历");
        iterativePreorderTraversal(root);
        System.out.println("递归方式中序遍历");
        recursiveInorderTraversal(root);
        System.out.println("非递归方式中序遍历");
        iterativeInorderTraversal(root);
        System.out.println("递归方式后序遍历");
        recursivePostorderTraversal(root);
        System.out.println("非递归方式后序遍历");
        iterativePostorderTraversal(root);
        System.out.println("递归方式宽度优先遍历");
        recursiveBreadthFirstTraversal(root);
        System.out.println("非递归方式宽度优先遍历");
        iterativeBreadthFirstTraversal(root);
        System.out.println("Morris遍历");
        morrisTraversal(root);


    }




    /**
     * 递归方式前序遍历
     */
    public static void recursivePreorderTraversal(Node head){
        //特殊情况，不在往下递归
        if (head == null){
           return;
        }

        System.out.println(head.value);
        recursivePreorderTraversal(head.left);
        recursivePreorderTraversal(head.right);
    }

    /**
     * 非递归方式前序遍历
     */
    public static void iterativePreorderTraversal(Node head){
        if (head == null){
            return;
        }

        /*
            前序遍历，直接输出当前节点
            然后将左右节点双端队列当中，从头开始放，先放右子节点，再放左左子节点
            然后从队列头部取出一个节点，输出，并将其子节点按照上面步骤放入
            如果没有子节点，就继续从队列头部取出一个节点，以此类推
         */
        Deque<Node> stack = new ArrayDeque<>();
        stack.addFirst(head);

        while (!stack.isEmpty()) {
            Node current = stack.pollFirst();
            System.out.println(current.value);

            // 注意顺序，先压入右子节点，再压入左子节点，以保证左子节点先被处理
            if (current.right != null) {
                stack.addFirst(current.right);
            }
            if (current.left != null) {
                stack.addFirst(current.left);
            }
        }

    }

    /**
     * 递归方式中序遍历
     */
    public static void recursiveInorderTraversal(Node head){
        if (head == null){
            return;
        }
        recursiveInorderTraversal(head.left);
        System.out.println(head.value);
        recursiveInorderTraversal(head.right);
    }

    /**
     * 非递归方式中序遍历
     */
    public static void iterativeInorderTraversal(Node head){
        if (head == null) {
            return;
        }

        /*
            中序遍历
            如果当前节点的左节点存在，就将当前节点放入栈中，
            如果当前节点没有左子节点，那么就输出当前节点，并查看是否有右子节点
            如果右子节点存在，就将右子节点放入栈中
            如果右节点不存在，就弹出一个
         */

        Deque<Node> stack = new ArrayDeque<>();
        Node current = head;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.addFirst(current);
                current = current.left;
            }

            current = stack.pollFirst();
            System.out.println(current.value);

            //这个非常的精髓，如果有右节点，那么就处理右节点
            //但是如果没有右节点，那么就是null，就会直接弹出下一个节点输出
            //不会出现反复遍历左边的情况
            current = current.right;
        }
    }

    /**
     * 递归方式后序遍历
     */
    public static void recursivePostorderTraversal(Node head){
        if (head == null){
            return;
        }

        recursivePostorderTraversal(head.left);
        recursivePostorderTraversal(head.right);
        System.out.println(head.value);
    }

    /**
     * 非递归方式后序遍历（难度最大，值得好好分析）
     */
    public static void iterativePostorderTraversal(Node head){
//        if (head == null) {
//            return;
//        }
//        Deque<Node> stack1 = new ArrayDeque<>();
//        Deque<Node> stack2 = new ArrayDeque<>();
//
//        Node current = head;
//        stack1.addFirst(current);
//        while (!stack1.isEmpty()){
//            current = stack1.peek();
//            while (current.left != null &&
//                    ((current.right != null && current.right != stack1.peek()) ||
//                    (current.right == null && current.left != stack1.peek()))){
//                stack1.addFirst(current);
//                current = current.left;
//            }
//
//            while (current.right != null && current.right != stack1.peek()){
//                stack1.addFirst(current);
//                current = current.right;
//            }
//
//            System.out.println(current.value);
//            stack1.pollFirst();
//            stack2.addFirst(current);
//        }

        if (head == null) {
            return;
        }

        Deque<Node<Integer>> stack = new ArrayDeque<>();
        Node<Integer> current = head;
        Node<Integer> lastVisited = null;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.addFirst(current);
                current = current.left;
            }

            current = stack.peek();

            if (current.right == null || current.right == lastVisited) {
                // 如果右子树为空或已经访问过右子树，则可以输出当前节点
                System.out.println(current.value);
                stack.pollFirst();
                lastVisited = current;
                current = null; // 继续弹栈
            } else {
                // 否则，处理右子树
                current = current.right;
            }
        }

    }

    /**
     * 非递归宽度优先遍历
     */
    public static void iterativeBreadthFirstTraversal(Node head){
        if (head == null){
            return;
        }

        Queue<Node> list = new LinkedList<>();
        Node cur = null;
        list.add(head);

        while (!list.isEmpty()){
            cur = list.poll();
            System.out.println(cur.value);
            if (cur.left != null){
                list.add(cur.left);
            }

            if (cur.right != null){
                list.add(cur.right);
            }
        }

    }

    /**
     * 递归方式宽度优先遍历
     */
    public static void breadthFirstTraversal(Queue<Node> queue){
        if (queue.isEmpty()){
            return;
        }

        Node cur = queue.poll();
        System.out.println(cur.value);

        if (cur.left != null){
            queue.add(cur.left);
        }

        if (cur.right != null){
            queue.add(cur.right);
        }

        breadthFirstTraversal(queue);

    }

    public static void recursiveBreadthFirstTraversal(Node head){
        if (head == null){
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        breadthFirstTraversal(queue);

    }

    /**
     * 打印二叉树
     */
    public static void printTree(Node root, int level) {
        if (root == null) {
            return;
        }

        printTree(root.right, level + 1);

        for (int i = 0; i < level; i++) {
            System.out.print("   ");  // 三个空格表示一个缩进
        }

        System.out.println(root.value);

        printTree(root.left, level + 1);
    }

    /**
     * 获取二叉树的深度
     */
    public static int getDepthOfTree(Node node){
        //base case
        if (node == null){
            return 0;
        }

        if (node.right == null && node.left == null){
            return 1;
        }

        int leftDepth = getDepthOfTree(node.left);
        int rightDepth = getDepthOfTree(node.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }

    /**
     * 叉树节点间的最大距离问题
     * 从二叉树的节点a出发，可以向上或者向下走，但沿途的节点只能经过一次，
     * 到达节点b时路径上的节点个数叫做a到b的距离，那么二叉树任何两个节点
     * 之间都有距离，求整棵树上的最大距离
     *
     * 分情况讨论：
     * 1.x节点不参与：
     *      Max{左数的最大距离，右数的最大距离}
     * 2.x参与
     *      左数高+右树高+1
     */
    public static int getMaxDistance(Node node){
        if (node == null){
            return 0;
        }

        int leftHeight = getDepthOfTree(node.left);
        int rightHeight = getDepthOfTree(node.right);
        int leftDistance = getMaxDistance(node.left);
        int rightDistance = getMaxDistance(node.right);
        int maxDistance = Math.max(1 + leftHeight + rightHeight, Math.max(leftDistance,rightDistance));

        return maxDistance;
    }

    /**
     * Morris遍历【中序】
     * Morris遍历是一种用于二叉树遍历的巧妙算法，它不需要使用递归或栈，同时也不需要修改树的结构。
     * 这种遍历算法的核心思想是在遍历的过程中，利用空闲指针将树中的节点连接起来，从而实现遍历。
     * 【Morris遍历的两大步骤】
     * 1.建立连接： 在遍历过程中，利用空闲指针（通常是节点的右子树的最右节点的右子树）将当前节点与
     * 其前驱节点连接起来。这样可以在遍历完成后，通过这些连接找到下一个要访问的节点。
     * 2.断开连接： 遍历完成后，为了不破坏树的结构，需要断开之前建立的连接。
     * 【遍历细节】
     * 假设来到当前节点cur，开始时cur来到头节点位置
     * 1.如果cur没有左孩子，那么cur向右移动（cur = cur.right）
     * 2.如果cur有左孩子，找到左子树上最右的节点mostRight：
     *      a.如果mostRight的右指针指向空，让其指向cur，
     *      然后cur向左移动（cur = cur.left）;
     *      b.如果mostRight的右指针指向cur，让其指向null，
     *      然后cur向左移动（cur = cur.right）;
     */
    public static void morrisTraversal(Node root){
        Node cur = root;
        Node mostRight = null;

        while (cur != null){
            if (cur.left == null){
                System.out.println(cur.value);
                cur = cur.right;
                continue;
            }

            mostRight = getMostRightNode(cur.left,cur);
            if (mostRight == null){
                mostRight.right = cur;
                cur = cur.left;
            }else {
                mostRight.right = null;
                System.out.println(cur.value);
                cur = cur.right;
            }
        }
    }

    /**
     * 辅助Morris遍历
     * 帮助其获得左子节点的最右节点，pre就是前面的节点
     * 必须保证mostRight ！= pre
     * @param leftNode
     * @param pre
     * @return
     */
    public static Node getMostRightNode(Node leftNode,Node pre){
        Node mostRight = leftNode;
        while (mostRight.right != null && mostRight.right != pre){
            mostRight = mostRight.right;
        }

        return mostRight;
    }
}

package day03;

import utils.MyUtils;
import utils.Node;

import java.util.*;

/**
 * @author zjx
 * @Date 2024-01-10 20:12:02
 * @Desc：
 * 该类主要有以下几点功能：
 * 1.判断一棵二叉树是否为搜索二叉树
 * 2.判断一棵二叉树是否为完全二叉树
 * 3.判断一棵二叉树是否为满二叉树
 * 4.判断一棵二叉树是否为平衡二叉树
 *
 * 【搜索二叉树（Binary Search Tree）】
 * 搜索二叉树（Binary Search Tree，BST）是一种二叉树的特殊形式，具有以下性质：
 * （1）对于树中的每个节点，其左子树中的每个节点的值都小于该节点的值。
 * （2）对于树中的每个节点，其右子树中的每个节点的值都大于该节点的值。
 * （3）左右子树都是搜索二叉树。
 *         10
 *        /  \
 *       5   15
 *      / \    \
 *     3   7    20
 *
 * 【完全二叉树（Complete Binary Tree）】
 * 叶子节点指挥出现再最后2层，且最后一层叶子节点都靠左对齐
 *          10
 *         /  \
 *        5   15
 *       / \
 *      3   7
 *
 * 【满二叉树（Full Binary Tree）】
 * 满二叉树是特殊的完全二叉树，其中每个节点要么没有子节点，要么有两个子节点，
 * 且所有叶子节点都在同一层。
 *         1
 *        / \
 *       2   3
 *      / \ / \
 *     4  5 6  7
 *
 * 【平衡二叉树（Balanced Binary Tree）】
 * 平衡二叉树（Balanced Binary Tree），又称为 AVL 树，具有以下性质：
 * （1）对于树中的每个节点，它的左子树和右子树的高度差不能超过1。
 * （2）左子树和右子树都必须是平衡二叉树。
 *
 */
public class BinaryTreeChecker {

    public static void main(String[] args) {
        Node<Integer> root = new Node<>(1);
        Node<Integer> n2 = new Node<>(2);
        Node<Integer> n3 = new Node<>(3);
        Node<Integer> n4 = new Node<>(4);
        Node<Integer> n5 = new Node<>(5);
        Node<Integer> n6 = new Node<>(6);
        root.left = n2;
        n2.left = n4;
        n4.left = n6;
        root.right = n3;
        n3.right = n5;

        System.out.println(isBalancedBinaryTreeIterative(root));
    }

    /**
     * 判断该树是否为搜索二叉树（Binary Search Tree）
     *
     * 搜索二叉树的性质：
     * 左子节点值 <= 父节点值
     * 右子节点值 >= 父节点值
     */
    public static boolean isBinarySearchTreeRecursive(Node<Integer> node){
        //base case
        if (node == null){
            return true;
        }

        //对于二叉搜索树来说，相同值的节点可以存在于左子树或右子树
        if ((node.left != null && node.left.value > node.value)
                        ||
            (node.right != null && node.right.value < node.value)){
            return false;
        }

        return isBinarySearchTreeRecursive(node.left) && isBinarySearchTreeRecursive(node.right);


    }




    /**
     * 非递归方式判断是否为搜索二叉树
     */
    public static boolean isBinarySearchTreeIterative(Node<Integer> head){
        if (head == null){
            return true;
        }

        //采用宽度优先的遍历方式来判断
        Node<Integer> cur = null;
        Queue<Node<Integer>> queue = new LinkedList<>();
        queue.add(head);

        while (!queue.isEmpty()){
            cur = queue.poll();

            //对左子节点进行判断
            if (cur.left != null){
                if (cur.left.value >= cur.value){
                    queue.add(cur.left);
                }else {
                    return false;
                }
            }

            //对右子节点进行判断
            if (cur.right != null){
                if (cur.right.value <= cur.value){
                    queue.add(cur.right);
                }else {
                    return false;
                }
            }

        }

        return true;

    }

    /**
     * 判断是否为完全二叉树
     *
     * 完全二叉树有如下性质：
     * 1.叶子节点只能出现再最后两行，并且最后一行的叶子节点要靠左
     */
    public static boolean isCompleteBinaryTree(Node<Integer> head){
        if (head == null){
            return true;
        }

        return isCompleteBinaryTreeRecursive(head,1,countNodes(head));

    }

    /**
     * 巧妙的运用完全二叉树的性质
     *
     * 根据完全二叉树的性质可知，如果按顺序标号，每一个位置的节点的标号，
     * 应该是连续的，中间没有空隙，最大的值，就是节点总数。一旦出现了空隙，
     * 就会造成标号 > 总数，那就一定不是完全二叉树。
     *
     * @param node 当前节点
     * @param index 节点标号
     * @param totalNum 二叉树节点总数
     * @return 是否为完全二叉树
     */
    public static boolean isCompleteBinaryTreeRecursive(Node<Integer> node,int index,int totalNum){
        //base case
        if (node == null){
            return true;
        }

        if (index > totalNum){
            return false;
        }

        return isCompleteBinaryTreeRecursive(node.left,2*index,totalNum) &&
                isCompleteBinaryTreeRecursive(node.right,index * 2 + 1,totalNum);
    }

    /**
     * 计算有多少个节点
     */
    private static <T> int countNodes(Node<T> node) {
        if (node == null) {
            return 0;
        }

        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public static boolean isCompleteBinaryTreeIterative(Node<Integer> head){
        if (head == null){
            return true;
        }

        //用于标记是否出现叶子节点或者只有一个子节点的节点
        boolean flag = false;
        //记录出现叶子节点后，往下的层数
        int level = 0;

        Node<Integer> cur = null;
        Queue<Node<Integer>> queue = new LinkedList<>();
        queue.add(head);

        //使用宽度优先遍历
        while (!queue.isEmpty()){
            cur = queue.poll();

            //有右子节点的时候，就必须有左子节点，否则不满足完全二叉树的性质
            if (cur.right != null && cur.left == null){
                return false;
            }


            if (cur.left != null){
                //当到达最后两层的时候，那么每下次一层就 level++，防止出现cur.l.l.l...的情况
                if (flag){
                    level++;
                }

                if (level >= 3){
                    return false;
                }
                queue.add(cur.left);
            }

            if (cur.right != null){
                queue.add(cur.right);
            }

            //只要右子节点为null，那么理论上，就应该来到了最后两层，flag 设为true
            //注意不要漏了 !flag ，否则每次到没有右子节点的时候，level都会被重置为1
            if (!flag && cur.right == null){
                flag = true;
                level = 1;
            }
        }

        return true;
    }



    /**
     * 判断是否为满二叉树
     *
     * 满二叉树的性质：
     * 1.满二叉树是完全二叉树的特殊情况
     * 2.如果出现叶子节点，那么这一整层，都应该是叶子节点。
     * 3.如果没有出现叶子节点，那么这一整层都应该有左右两个子节点
     */
    public static boolean isFullBinaryTreeIterative(Node<Integer> head){
        if (head == null){
            return true;
        }

        //标记是否出现叶子节点
        boolean hasLeafNode = false;

        Node<Integer> cur = null;
        Queue<Node<Integer>> queue = new LinkedList<>();
        queue.add(head);

        //使用宽度遍历
        while (!queue.isEmpty()){
            cur = queue.poll();

            //该节点是否为叶子节点
            if (!hasLeafNode && (cur.left == null || cur.right == null)){
                hasLeafNode = true;
            }

            //如果已经出现叶子节点，那么后面所有的节点，都必须是叶子节点
            if (hasLeafNode){
                if (cur.right != null || cur.left != null){
                    return false;
                }
            }else {
                //如果还没有出现叶子节点，那么该节点一定同时存在左右两个子节点

                //这里不需要这个判断，因为永远都是false
                //if (cur.right == null || cur.left == null) {
                //    return false;
                //}

                queue.add(cur.left);

                queue.add(cur.right);
            }
        }

        return true;
    }

    public static boolean isFullBinaryTree(Node<Integer> head){
        if (head == null){
            return true;
        }

        int nodeNum = countNodes(head);

        //如果 (nodeNum + 1) 不是2的整次幂，那么说明有问题
        int powerOfTwo = nodeNum + 1;

        if (!((powerOfTwo & (powerOfTwo -1)) == 0)){
            return false;
        }

        return isFullBinaryTreeRecursive(head,1,nodeNum);
    }

    /**
     * 原理和完全二叉树相似
     */
    public static boolean isFullBinaryTreeRecursive(Node<Integer> node,int index,int totalNum){
        //base case
        if (node == null){
            return true;
        }

        if (index > totalNum){
            return false;
        }

        return isFullBinaryTreeRecursive(node.left,2 * index,totalNum) &
                isFullBinaryTreeRecursive(node.right,2 * index + 1,totalNum);
    }



    /**
     * 判断是否为平衡二叉树
     */
    public static boolean isBalancedBinaryTreeRecursive(Node<Integer> node){
        if (node == null){
            return true;
        }

        int leftHeight = MyUtils.getDepthOfNode(node.left);
        int rightHeight = MyUtils.getDepthOfNode(node.right);

        if (Math.abs(leftHeight - rightHeight) > 1){
            return false;
        }

        return isBalancedBinaryTreeRecursive(node.left) && isBalancedBinaryTreeRecursive(node.right);
    }

    public static boolean isBalancedBinaryTreeIterative(Node<Integer> root){
        if (root == null) {
            return true;
        }

        Node<Integer> cur = root;
        Deque<Node<Integer>> stack = new ArrayDeque<>();
        Node<Integer> pre = null;

        //使用后序遍历
        /*while (!stack.isEmpty() || cur != null){
            while (cur != null){
                stack.addFirst(cur);
                cur = cur.left;
            }

            cur = stack.peek();

            if (cur.right != null && cur.right != pre){
                cur = cur.right;
            }else {
                int leftHeight = cur.left != null ? MyUtils.getDepthOfNode(cur.left) : 0;
                int rightHeight = cur.right != null ? MyUtils.getDepthOfNode(cur.right) : 0;

                if (Math.abs(leftHeight - rightHeight) > 1){
                    return false;
                }

                pre = cur;
                stack.pollFirst();
                cur = null;
            }

        }*/

        //宽度优先遍历，更简单，这里使用后序遍历，主要是后序遍历比较难
        //我将借机复习一下罢了

        return true;


    }

}

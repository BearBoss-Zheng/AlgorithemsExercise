package day03;

import utils.Node;

/**
 * @author zjx
 * @Date 2024-01-09 18:46:17
 * @Desc :
 * 【题目】给定两个可能有环也可能无环的单链表，头节点 head1 和 head2。请实现一个函数，如果两个
 * 链表相交，请返回相交的第一个结点。如果不相交，返回null
 * 【要求】如果两个链表长度之和位N，时间复杂度请达到 o(N)，额外空间复杂度请达到 o(1)。

 * 思路分析：
 * 1. 如果一个链表有环，另一个链表没环，那么两个一定不相交
 * 2. 如果两个链表都没有环，如果相交，就一定是 “Y” 字型，所以可以让长的链表，先走多出来的几步，
 *    然后两个链表一起走，就一定能在第一个相交点汇合，如果没有汇合，说明不相交
 * 3. 如果两个链表都有环，
 */
public class LinkedListIntersectionSolver {

    public static void main(String[] args) {
        Node<Integer> n1 = new Node<Integer>(1);
        Node<Integer> n2 = new Node<Integer>(2);
        Node<Integer> n3 = new Node<Integer>(3);
        Node<Integer> n4 = new Node<Integer>(4);
        Node<Integer> n5 = new Node<Integer>(5);
        Node<Integer> n6 = new Node<Integer>(6);
        Node<Integer> n7 = new Node<Integer>(7);
        Node<Integer> n8 = new Node<Integer>(8);
        Node<Integer> n9 = new Node<Integer>(9);
        Node<Integer> n10 = new Node<Integer>(10);
        Node<Integer> n11 = new Node<Integer>(11);

        n1.next = n2;
        n2.next = n5;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;
        n8.next = n9;
        n9.next = n10;
        n10.next = n11;
        n11.next = n5;

        Node intersectionNode = findIntersectionNode(n1, n2);

        System.out.println(intersectionNode);

    }



    /**
     * 判断两个链表是否相交，并返回相交的第一个节点
     *
     * @param head1 第一个链表的头节点
     * @param head2 第二个链表的头节点
     * @return 相交的第一个点，如果不相交，返回 null
     */
    public static Node findIntersectionNode(Node head1,Node head2){
        //特殊情况
        if (head1 == null || head2 == null){
            return null;
        }

        //先判断链表是否有环
        boolean isL1 = isLoop(head1);
        boolean isL2 = isLoop(head2);
        //一个有环，一个无环，那么一定没有相交节点
        if (isL1 ^ isL2){
            return null;
        }

        Node<Integer> res = null;

        //如果两个都是无环的
        if (!isL1){
            res = intersectionNodeOfTwoAcyclic(head1,head2,null);
        }

        //如果是两个都是有环的
        if (isL1){
            res = intersectionNodeOfTwoLoop(head1,head2);
        }

        return res;
    }


    /**
     * 判断链表是否有环
     *
     * @param head 链表头节点
     * @return 如果有环返回 true ；如果没有环返回 false
     */
    public static boolean isLoop(Node head){
        //使用快慢指针慢指针每次走一步，快指针每次走两步
        //如果是有环链表，总有一个时刻，快指针 == 慢指针
        //如果是无环链表，那么总有一个时刻，，快慢指针会走到尽头
        Node slow = head;
        Node fast = head;
        while (slow.next != null && fast.next!= null &&fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow){
                return true;
            }
        }

        return false;
    }

    /**
     * 查找两个无环链表的相交节点
     * @param head1 链表1 的头节点
     * @param head2 链表2 的头节点
     * @param end 如果是两个无环结构，end==null，如果是有环结构，end可能是相遇点，也可能在相遇点之后
     * @return 返回相遇点，不相遇则返回null
     */
    public static Node intersectionNodeOfTwoAcyclic(Node head1,Node head2,Node end){
        //链表1的长度
        int length1 = getLength(head1,end);
        //链表2的长度
        int length2 = getLength(head2,end);
        //两者的指针
        Node cur1 = head1;
        Node cur2 = head2;
        //两者的差值
        int dif = Math.abs(length1-length2);
        //长的链表先走 dif 步数
        if (length1 > length2){
            while (dif != 0){
                cur1 = cur1.next;
                dif--;
            }
        }

        if (length2 > length1){
            while (dif != 0){
                cur2 = cur2.next;
                dif--;
            }
        }

        //两个一起走,如果是无环链表，在这就结束了
        while (cur1!= end && cur2 != end){
            cur1 = cur1.next;
            cur2 = cur2.next;
            //如果相等，则在第一个相交点相遇
            if (cur1 == cur2){
                return cur1;
            }
        }

        //如果是有环链表，需要追加一个判定条件，因为end就是他们的相遇点
        if (end != null){
            return end;
        }

        //如果在第一条链表到了终点都没有相遇。说明不相交
        return null;

    }

    /**
     * 查找两个有环链表的相交节点
     */
    private static Node intersectionNodeOfTwoLoop(Node head1, Node head2) {
        //有环问题，有两种情况：
        //情况一：相交
        //如果是有环的，可以把有环问题转换成无环问题
        //两条链表，一条使用快指针，另一条使用慢指针，那么二者一定会在环内某一个点相遇，令该点为inter1
        //而初次相遇的点，一定在头节点到inter1之间，那么就可以把两个有环结构，看成两个head - inter1的无环结构
        //情况二：不相交
        //如果不相交，就不能使用快慢指针找出环内相交点，所以需要判断是否相交
        //判断是否相交可以使用快慢指针先得到环内的一个点，如果另一个链表也有这个点，那么必定相交
        //如果另一个链表没有这个点，那么一定不相交

        //记录相交环内的一点
        Node inter1 = null;
        //分别找到两个链表环内的一点
        Node cycle1 = nodeInCycle(head1);
        Node cycle2 = nodeInCycle(head2);
        //cycle2的指针
        Node cur = cycle2.next;
        //遍历cycle2，看看cycle1点在不在其中
        while (cur != cycle2){
            cur = cur.next;
            if (cur == cycle1){
                inter1 = cur;
                break;
            }
        }


        if (inter1 == null){
            return null;
        }else {
            return intersectionNodeOfTwoAcyclic(head1,head2,inter1);
        }


    }

    /**
     * 获取链表一个点到另一个点的距离
     */
    public static int getLength(Node from,Node to){
        if (from == null){
            return -1;
        }

        int length = 1;
        Node cur = from;

        //如果to为null，就是走到底
        while (cur.next != to){
            cur = cur.next;
            length++;
        }


        return length;
    }

    /**
     * 查找环内的一定
     */
    public static Node nodeInCycle(Node head){
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


}

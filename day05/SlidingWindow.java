package day05;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @author zjx
 * @Date 2024-01-12 22:43:13
 * @Desc:
 * 滑动窗口
 * 【题目】
 * 有一个整形数组arr和一个大小为w的窗口从数组的最坐标滑倒最右边，窗口每次向右边滑动一个位置。
 * 例如，数组为{4，3，5，4，3，3，6，7}，窗口大小为3时：
 * [4 3 5] 4 3 3 6 7    窗口中的最大值为 5
 * 4 [3 5 4] 3 3 6 7    窗口中的最大值为 5
 * 4 3 [5 4 3] 3 6 7    窗口中的最大值为 5
 * 4 3 5 [4 3 3] 6 7    窗口中的最大值为 4
 * 4 3 5 4 [3 3 6] 7    窗口中的最大值为 6
 * 4 3 5 4 3 [3 6 7]    窗口中的最大值为 7
 * 如果数组长度为n，窗口大小为w，则一共产生 n-w+1 个窗口的最大值。
 * 请实现一个函数。输入：整形数组arr，窗口大小为 w。
 * 输出：一个长度为 n-w+1 的数组res，res[i]表示每一种窗口状态下的最大值。以本题为例，结果应该
 * 返回{5，5，5，4，6，7}
 */
public class SlidingWindow {

    public static void main(String[] args) {
        int[] nums = {4,3,5,4,3,3,6,7};
        int[] ints1 = slidingWindowMaxValue(nums, 3);
        System.out.println(Arrays.toString(ints1));
    }

    /**
     * 【思路】
     * 利用双端队列，饰演一个严格单调减的单调栈，记录数组的加标
     * 具体怎么维护单调性如下：
     * 1.如果要添加的数，比头节点大或者等于，那么直接弹出头节点，从队列左边添加该节点
     * 2.如果要添加的数，小于头节点，那么就看尾巴，如果添加的数比尾巴大，或等于，那么就弹出尾巴，
     *   因为这些数永远没有机会成为最大值了，一直弹到尾巴的数大于这个数为止
     * 【注意】
     * 这里存放的是坐标，方标处理过期的元素
     */
    public static int[] slidingWindowMaxValue(int[] nums, int windowSize) {
        if (windowSize > nums.length || windowSize <= 0) {
            System.out.println("Invalid window size");
            return null;
        }

        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - windowSize + 1];

        for (int i = 0; i < nums.length; i++) {
            // Remove elements that are out of the current window
            while (!deque.isEmpty() && deque.peek() < i - windowSize + 1) {
                deque.pollFirst();
            }

            // Remove elements smaller than the current element from the back
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add the current element to the deque
            deque.addLast(i);

            // Store the maximum value for the current window
            if (i >= windowSize - 1) {
                result[i - windowSize + 1] = nums[deque.peek()];
            }
        }

        return result;
    }

}

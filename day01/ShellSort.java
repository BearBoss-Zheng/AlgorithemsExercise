package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-27 00:01:18
 * @Desc : 希尔排序
 * 希尔排序（Shell Sort）是插入排序的一种改进版本，也被称为缩小增量排序。
 * 它的基本思想是通过定义一个间隔序列（称为增量序列），对原始序列进行多次插入
 * 排序，每次排序时只对间隔为增量的元素进行操作。随着排序的进行，逐渐缩小增量，
 * 直到增量为1，此时进行一次常规插入排序，整个序列就变成有序的。

 * 具体步骤如下：

 * 1.选择增量序列： 选择一个增量序列，例如希尔建议的增量序列（1, 3, 7, 15，...）。

 * 2.按增量进行插入排序： 对每个增量分组进行插入排序，即将相隔增量的元素组成一个子序列，
 * 对子序列进行插入排序。

 * 3.缩小增量： 缩小增量，重复第2步，直到增量为1。
 *
 * 4.最后一次插入排序： 当增量为1时，进行一次常规的插入排序，此时序列已经基本有序。
 */
public class ShellSort {
    public static void main(String[] args) {
        //测试5000组数据
        for (int i = 0; i < 5000; i++) {
            //数据大小随机[0,5000]
            int size =(int) (Math.random()*5000)+1;
            int[] arr = MyUtils.createArr(size, 0, 4000);
            // 创建一个新数组，将arr的内容复制到新数组
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            Arrays.sort(arr2);
            sort2(arr);
            //无脑测试
            if (!Arrays.equals(arr2,arr)){
                System.out.println("有错误产生");
                return;
            }
        }
        System.out.println("perfect!!!");
    }

    public static void sort(int[] nums){
        //特殊情况
        if (nums == null || nums.length <= 1){
            return;
        }
        //组间距
        for (int groupSize = nums.length/2; groupSize >= 1; groupSize/=2) {
            //这一层可以理解为，把一个数组，分成了几个子数组，每个数组的大小是groupSize
            for (int i = 0; i < groupSize; i++) {
                //这一层就是对每一个子数组进行插入排序
                /*
                    从i+group开始，就像插入排序从1开始，而不是0，因为第一数字不知道插入，他就已经是有序了
                 */
                for (int j = i + groupSize; j < nums.length; j+=groupSize) {
                    //插入排序
                    int index = j;
                    while (index >= 0 && index-groupSize >=0){
                        if (nums[index] < nums[index-groupSize]){
                            MyUtils.swap(nums,index,index-groupSize);
                            index-=groupSize;
                        }else {
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * 优化后
     */
    public static void sort1(int[] arr){
        int n = arr.length;

        // 初始间隔设定为数组长度的一半，然后逐步减小间隔
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 对每个间隔进行插入排序
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;

                // 在当前间隔下，对元素进行比较和交换
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }

                // 将元素插入到正确的位置
                arr[j] = temp;
            }
        }
    }

    /**
     * 复习
     */
    public static void sort2(int[] nums){
        if (nums == null || nums.length <= 1){
            return;
        }

        for (int groupSize = nums.length/2; groupSize >0 ; groupSize /= 2) {
            for (int i = groupSize; i < 2* groupSize ; i++) {
                for (int j = i; j < nums.length; j += groupSize) {
                    int index = j;
                    while (index-groupSize >= 0 && nums[index] < nums[index-groupSize]){
                        MyUtils.swap(nums,index,index-groupSize);
                        index -= groupSize;
                    }
                }
            }

        }
    }
}

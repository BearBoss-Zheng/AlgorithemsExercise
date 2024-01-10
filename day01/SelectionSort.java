package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-26 23:21:56
 * @Desc ： 选择排序
 * 基本思想是将待排序的序列分成两部分：
 * 已排序部分和未排序部分。在未排序部分选择最小（或最大）的元素，然后将其与未排序部分
 * 的第一个元素交换位置，将其纳入已排序部分。这样，逐渐将未排序部分的最小元素逐步纳入
 * 已排序部分，直至整个序列排序完成。

 * 具体步骤如下：

 * 1.初始状态： 将整个序列分为已排序部分和未排序部分，初始时已排序部分为空。

 * 2.选择最小元素： 在未排序部分中找到最小的元素。

 * 3.交换位置： 将最小元素与未排序部分的第一个元素交换位置。

 * 4.扩大已排序部分： 将已排序部分的末尾扩展，包括刚刚交换过来的最小元素。
 *
 * 5.重复： 重复以上步骤，直到未排序部分为空，整个序列排序完成
 */
public class SelectionSort {
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
            sort1(arr);
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

        //本质上，没循环一次，属于num[i]的数字就确定好了
        /*
            num[0] 是 第一小的
            num[1] 是 第二小的
            num[2] 是 第三小的
            ...
         */
        for (int i = 0; i < nums.length-1; i++) {
            //记录最小值的坐标
            int index = i;
            //记录最小值的值
            int min = nums[i];
            //寻找[i,num.length-1]上的最小值
            for (int j = i; j < nums.length; j++) {
                if (min > nums[j]){
                    min = nums[j];
                    index = j;
                }
            }
            //交换位置
            if (index != i){
                MyUtils.swap(nums,i,index);
            }

        }
    }

    public static void sort1(int[] nums){
        for (int i = 0; i < nums.length; i++) {
            int min = nums[i];
            int index = i;
            //找最小值
            for (int j = i; j < nums.length; j++) {
                if (min > nums[j]){
                    min = nums[j];
                    index = j;
                }
            }
            //替换
            MyUtils.swap(nums,i,index);
        }
    }
}

package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-26 22:28:01
 * @Desc 冒泡排序
 * 思路：
 * 具体步骤如下：
 * 1.比较相邻元素： 从列表的第一个元素开始，比较它与下一个元素的大小。

 * 2.交换位置： 如果第一个元素比第二个元素大（升序排序），则交换它们的位置。
 *   如果是降序排序，那么如果第一个元素比第二个元素小就交换它们的位置。

 * 3.移动到下一组相邻元素： 继续比较和交换第二个与第三个元素，然后第三个与第四个元素，
 *   依此类推，直到达到列表的末尾。

 * 4.重复：重复以上步骤，直到不再需要交换元素，也就是列表已经排序完成。

 *              时间复杂度       空间复杂度       是否稳定
 * 冒泡排序         o(n²)           o(1)           ✔
 */
public class BubbleSort {
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

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length-1-i; j++) {
                if (nums[j] > nums[j+1]){
                    MyUtils.swap(nums,j,j+1);
                }
            }
        }
    }

    public static void sort1(int[] nums){
       if (nums == null || nums.length <= 1){
           return;
       }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i-1; j++) {
                if (nums[j] > nums[j+1]){
                    MyUtils.swap(nums,j,j+1);
                }
            }
        }
    }
}

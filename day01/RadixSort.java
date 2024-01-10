package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-28 00:20:12
 * @Desc : 基数排序
 * Radix Sort（基数排序）是一种非比较性的整数排序算法，它将整数按照位数进行排序。
 * 基数排序的思想是从最低有效位（最右边）到最高有效位（最左边）进行排序，每一轮
 * 排序都是一次稳定的桶排序。

 * 基数排序的步骤如下：

 * 1.确定最大数的位数： 遍历整个数组，找到最大的数字并确定其位数。

 * 2.按位排序： 从最低有效位到最高有效位，依次对每一位进行桶排序或计数排序。

 * 3.重复步骤2： 对所有位进行排序，直到最高有效位。

 * 4.得到有序序列： 最终得到有序的整数序列。

 * 基数排序的关键在于按位排序，它可以使用桶排序或计数排序来实现。基数排序适用于对整数进行排序，
 * 但对于负数需要特殊处理。
 */
public class RadixSort {
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

        if (nums == null || nums.length <= 1){
            return;
        }

        //找到最大值
        int max = MyUtils.getMax(nums);

        //按位排序
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(nums,exp);
        }

    }

    public static void countingSort(int[] nums,int exp){
        int[] count = new int[10];
        int[] output = new int[nums.length];

        //统计每个桶的数量
        for (int i = 0; i < nums.length; i++) {
            count[(nums[i] / exp)%10]++;
        }

        //计算桶的累积数量
        for (int i = 1; i < 10; i++) {
            count[i] += count[i-1];
        }

        //从后往前遍历原数组，将元素放入相应的桶中
        for (int i = nums.length-1 ; i >= 0; i--) {
            output[count[(nums[i] / exp) % 10] -1] = nums[i];
            count[(nums[i] / exp) % 10]--;
        }

        System.arraycopy(output,0,nums,0,nums.length);

    }

    public static void sort1(int[] nums){
        if (nums == null || nums.length <= 1){
            return;
        }

        int max = MyUtils.getMax(nums);


        //按位排序，个位、十位、百位...
        for (int exp = 1; max/exp > 0; exp*=10) {
            //统计该位上，不同数字的个数
            int[] count = new int[10];

            for (int num : nums) {
                //例如判定十位数的时候，就是 (num/10)%10
                count[(num / exp)%10]++;
            }

            for (int k = 1; k < 10; k++) {
                count[k] += count[k - 1];
            }

            //按该位排序所得结果
            int[] cur = new int[nums.length];
            for (int j = nums.length-1; j >= 0; j--) {
                int index = --count[(nums[j]/exp)%10];
                cur[index] = nums[j];
            }

            //将元素组替换为cur
            System.arraycopy(cur,0,nums,0,nums.length);
        }

    }



}

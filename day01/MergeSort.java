package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-26 23:54:59
 * @Desc : 归并排序
 * 基本思想是将待排序的序列划分成若干子序列，分别进行排序，然后将已排序的
 * 子序列合并成一个有序的序列。

 * 具体步骤如下：

 * 1.划分序列： 将待排序的序列划分为两个子序列，直到每个子序列只有一个元素。

 * 2.递归排序： 对每个子序列进行递归排序，直到所有子序列都有序。

 * 3.合并序列： 将两个有序的子序列合并成一个有序序列。合并过程是比较两个子序
 * 列的元素，将较小的元素放入临时数组中，直到其中一个子序列为空，然后将剩余的元
 * 素直接复制到临时数组中。

 * 4.重复合并： 重复以上步骤，直到所有子序列合并成一个完整的有序序列。
 */
public class MergeSort {
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
        split(nums,0, nums.length-1);
    }

    /**
     * 拆分数组
     * @param nums 数组
     * @param begin 拆分起始坐标
     * @param end 拆分的结束坐标
     */
    public static void split(int[] nums,int begin,int end){
        int mid = (begin+end)/2;
        if (begin != end){
            //对左边进行拆分排序
            split(nums,begin,mid);
            //对右边进行拆分排序
            split(nums,mid+1,end);
            //合并左右两边
            merger(nums,begin,mid,mid+1,end);
        }

    }

    /**
     * 合并两个子数组
     * @param nums 数组（子数组包含在数组当中）
     * @param b1 第一个数组的起始坐标
     * @param e1 第一个数组的结束坐标
     * @param b2 第二个数组的起始坐标
     * @param e2 第二个数组的结束坐标
     */
    public static void merger(int[] nums,int b1,int e1,int b2,int e2){
        int index1 = b1;
        int index2 = b2;
        int[] res = new int[e2-b1+1];
        int cur = 0;
        // 比较左右两个子数组的元素，并将较小的元素放入原始数组
        while (index1<=e1 && index2<=e2){
            if (nums[index1]<=nums[index2]){
                res[cur++] = nums[index1++];
            }else {
                res[cur++] = nums[index2++];
            }
        }

        if (index1>e1){
            //处理右子数组中剩余的元素
            for (int i = index2; i <=e2; i++) {
                res[cur++] = nums[i];
            }
        }else {
            //处理坐子数组中剩余的元素
            for (int i = index1; i <=e1; i++) {
                res[cur++] = nums[i];
            }
        }

        System.arraycopy(res,0,nums,b1,e2-b1+1);
    }

    //复习
    public static void sort1(int[] nums){
        if (nums == null || nums.length <= 1){
            return;
        }

        split1(nums,0, nums.length-1);
    }

    /**
     * 拆分数组
     * @param nums 数组
     * @param begin 拆分起始坐标
     * @param end 拆分的结束坐标
     */
    public static void split1(int[] nums,int begin,int end){
        //结束的判定条件：无法继续拆分了
        if(begin == end){
            return;
        }

        int mid = (begin + end) /2;

        //拆分
        split1(nums,begin,mid);
        split1(nums,mid+1,end);

        //合并
        merger1(nums,begin,mid,end);

    }

    /**
     * 将两个数组合并为有序数组
     * @param nums 数组
     * @param begin 开始坐标
     * @param mid 分割点（mid在左数组内，mid+1在右数组内）
     * @param end 结束点
     */
    private static void merger1(int[] nums, int begin, int mid, int end) {
        //临时存放排序数组
        int[] res = new int[end - begin +1];
        //左数组指针
        int left = begin;
        //右数组指针
        int right = mid + 1;
        //res的坐标
        int cur = 0;
        //进行初步排序，两个数组的值插入res值
        while (left <= mid && right <= end){
            //开始进行判断，有如下几种情况
            //1.左 > 右，则将右边插入res，右指针往后移，左指针不动
            //2.左 < 右，则将左边插入res，左指针往后移，右指针不动
            //3.左 = 右，则将左边插入res，左指针往后移，右指针不动
            if (nums[left] <= nums[right]){
                res[cur++] = nums[left];
                left++;
            }else {
                res[cur++] = nums[right];
                right++;
            }
        }

        //有一边的数组已经全部插入res了
        //左边数组已经全部插入，此时只需要把右边数组的值全部插入即可
        if (left > mid){
            for (int i = cur; i < res.length; i++) {
                res[i] = nums[right++];
            }
        }else {
            //右边数组已经全部插入，此时只需要把左边数组的值全部插入即可
            for (int i = cur; i < res.length; i++) {
                res[i] = nums[left++];
            }
        }

        //排序完成，将nums中的数替换为res
        System.arraycopy(res,0,nums,begin,end-begin+1);
    }


}

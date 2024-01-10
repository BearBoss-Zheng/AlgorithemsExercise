package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-27 21:55:56
 * @Desc : 堆排序
 * 堆排序是一种基于二叉堆数据结构的排序算法。它的基本思想是通过构建二叉堆（最大堆或最小堆）
 * 来实现排序。堆排序可以分为两个主要阶段：建堆和排序。

 * 1. 建堆阶段：
 * 构建堆： 将待排序的数组看成是一颗完全二叉树，通过从最后一个非叶子节点开始，依次向前调整
 * 每个子树，使得每个子树都满足堆的性质（最大堆或最小堆）。

 * 调整堆： 从最后一个非叶子节点开始，依次向前调整每个子树，使得每个子树都满足堆的性质。
 * 这一步通常称为堆化（heapify）。

 * 2. 排序阶段：
 * 交换根节点与末尾元素： 将堆顶元素与数组末尾元素交换，此时末尾元素是最大值（最大堆）或
 * 最小值（最小堆）。

 * 调整堆： 将剩余的元素重新调整成堆，重复此过程直到整个数组有序。
 */
public class HeapSort {
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

        for (int i = 0; i < nums.length; i++) {
            heapInsert(nums,i);
        }

        for (int i = 0; i < nums.length; i++) {
            int size = nums.length-i;
            heapify(nums,0,size);
            MyUtils.swap(nums,0,size-1);
        }
    }


    /**
     * 构建大根堆
     * @param nums 数组
     * @param index 插入的元素坐标
     */
    public static void heapInsert(int[] nums,int index){

        while (nums[index] > nums[(index-1)/2]){
            MyUtils.swap(nums,index,(index-1)/2);
            index = (index-1)/2;
        }

    }


    /**
     * 所谓的堆化，就是在指定的 size 范围内，重新调整为大根堆
     * @param nums 数组
     * @param index 根节点坐标
     * @param size 参与堆化的数组大小
     */
    public static void heapify(int[] nums,int index,int size){
        //左子节点
        int left = (index * 2) + 1;
        while (left < size){
            //右子节点
            int right = left + 1;
            //找出左右子节点的最大值
            int largest = right < size && nums[left] < nums[right] ? right : left ;
            //与根节点比较，找出最大值
            largest = nums[largest] > nums[index] ? largest : index;
            if (largest == index){
                return;
            }
            MyUtils.swap(nums,largest,index);
            index = largest;
            left = (index * 2) + 1;
        }
    }


    public static void sort1(int[] nums){
        if (nums == null || nums.length <=1){
            return;
        }

        //构建大根堆
        for (int i = 0; i < nums.length; i++) {
            heapInsert1(nums,i);
        }

        //每次都调整为大根堆，然后与范围内最后一个位置交换
        for (int i = 0; i < nums.length; i++) {
            int size = nums.length-i;
            //heapify(nums,0,size);
            heapify1(nums,size,0);
            MyUtils.swap(nums,size-1,0);
        }

    }

    /**
     * 构建大根堆
     * @param nums 数组
     * @param index 插入的元素坐标
     */
    public static void heapInsert1(int[] nums,int index){
        //特殊情况
        if (nums==null || index >= nums.length){
            return;
        }

        //大根堆得是最大的
        while (nums[index] > nums[(index-1)/2]){
            MyUtils.swap(nums,index,(index-1)/2);
            index = (index-1)/2;
        }
    }


    /**
     * 所谓的堆化，就是在指定的 size 范围内，重新调整为大根堆
     * @param nums 数组
     * @param size 范围
     * @param index 根节点
     */
    public static void heapify1(int[] nums,int size,int index){
        //特殊判定条件
        if (nums == null || nums.length <= 1 || index == size-1){
            return;
        }

        //左子节点
        int left = index * 2 +1;

        //找到最大值的坐标
        while (left < size){
            //右节点
            int right = left + 1;
            int large = nums[left] > nums[index] ? left : index;
            large = right < size ?
                    (nums[large] > nums[right] ? large :  right )
                        :
                    large;

            //只要满足这个条件，就还是大根堆
            if (large == index){
                return;
            }

            //重新调整为大根堆
            MyUtils.swap(nums,large,index);
            index = large;
            left = 2 * index + 1;

        }


    }
}

package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-26 23:47:04
 * @Desc ： 插入排序
 * 基本思想是将待排序的序列分成已排序部分和未排序部分。初始时，已排序部分只包含一个
 * 元素（通常是序列的第一个元素），然后逐步将未排序部分的元素插入到已排序部分，使得
 * 已排序部分仍然保持有序。

 * 具体步骤如下：

 * 1.初始状态： 将序列的第一个元素视为已排序部分，其余元素为未排序部分。

 * 2.从未排序部分取出元素： 从未排序部分取出一个元素，将其插入到已排序部分的合适位置。

 * 3.比较并移动： 将取出的元素与已排序部分的元素依次比较，找到合适的位置插入，
 * 同时移动其他元素以腾出插入位置。

 * 4.扩大已排序部分： 将已排序部分的末尾扩展，包括刚刚插入的元素。
 *
 * 5.重复： 重复以上步骤，直到未排序部分为空，整个序列排序完成。
 */
public class InsertionSort {
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
            sort(arr);
            //对数器
            if (!Arrays.equals(arr2,arr)){
                System.out.println("有错误产生");
                return;
            }
        }
        System.out.println("perfect!!!");

    }

    /**
     * sort1 写的更好
     * @param nums 数组
     */
    public static void sort(int[] nums){
        //特殊情况
        if (nums == null || nums.length <= 1){
            return;
        }

        //每循环一次，已排序的数组的数量增加1，实质上，就是为num[i],找一个合适的位置
        for (int i = 1; i < nums.length; i++) {
            //在(0,i)位置上，找一个合适 num[i]的位置，
            //即：前面一个<=num[i]<=后面一个，当遇到比num[i]大的，就继续往前走，
            //直到遇到比num[i]小或者相等的数
            int index = i;
            //这里的判定条件不好，建议参考sort1的，差别就在这儿
            while (index != 0){
                //比num[index]大，就交换位置
                if (nums[index] < nums[index -1]){
                    MyUtils.swap(nums,index,index-1);
                    index--;
                }else {
                    //现在的位置就是适当的位置
                    break;
                }

            }
        }
    }

    public static void sort1(int[] nums){
        //特殊情况
        if (nums == null || nums.length <= 1){
            return;
        }

        //将 i 位置上的元素，放在[0,i]上的正确位置，让[0,i]上的位置有序
        for (int i = 1; i < nums.length; i++) {
            while (i-1 >= 0 && nums[i] < nums[i-1]){
                MyUtils.swap(nums,i,i-1);
                i--;
            }
        }
    }
}

package day01;

import utils.MyUtils;

import java.util.Arrays;

/**
 * @author zjx
 * @Date 2023-12-27 21:13:56
 * @Desc : 快速排序
 * 快排是一种常用的基于比较的排序算法，它采用分治的思想。这个算法的主要步骤如下：

 * 1.选择一个基准元素（Pivot）： 从数组中选择一个元素作为基准，通常选择数组中间的元素。

 * 2.划分过程（Partition）： 将数组分割成两个子数组，使得左边的子数组中的元素都小于等于基
 * 准元素，右边的子数组中的元素都大于等于基准元素。基准元素在这个过程中被放置在了最终的位置。

 * 3.递归排序： 递归地对左右两个子数组进行快速排序。

 * 4.合并结果： 不需要合并步骤，因为在划分的过程中就已经将数组排序。
 */
public class QuickSort {
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
        sort(nums,0, nums.length-1);
    }

    public static void sort(int[] nums, int L, int R) {
        if (L < R) {
            //将L~R中间的一个数与R位置的数交换，降低时间复杂度
            MyUtils.swap(nums, L + (int) (Math.random() * (R - L + 1)), R);
            int[] p = partition(nums, L, R);
            sort(nums, L, p[0]);
            sort(nums, p[1], R);
        }
    }

    /**
     * 分割数组，左边的数小于pivot，右边的数大于pivot
     * pivot：初始数组的中间值
     * @param nums 数组
     * @param begin 子数组的起始位置
     * @param end 子数组的结束位置
     * @return  标志点的位置，即pivot的位置
     */
    public static int[] partition(int[] nums, int begin,int end) {
        //less的左边（包括less），都是比基准值小的
        int less = begin -1;
        //more的右边（包括more），都是比基准值大的
        int more = end;
        //基准值
        int pivot = nums[end];
        //begin < more ，从more开始，都是比pivot大的值，不需要继续往前判断
        while (begin < more){
            if (nums[begin] > pivot){
                //如果nums[begin]大于pivot，就应该放在more的范围当中
                //即more向前移动一位，并且将当前的位置的数值和nums[begin]交换
                MyUtils.swap(nums,begin,--more);
            } else if (nums[begin] < pivot) {
                //如果nums[begin]小于于pivot，就应该放在less的范围当中
                //即less向前移动一位，并且将当前的位置的数值和nums[begin]交换
                MyUtils.swap(nums,++less,begin++);
            }else {
                //如果相等，begin继续往前即可，less和more不需要移动
                begin++;
            }
        }

        //最后需要把pivot放在正确的位置上,more其实是多了一个的
        //因为pivot包含在more当中，所以只要more里面的第一个和pivot交换就行了
        MyUtils.swap(nums, more,end);

        return new int[]{less,more};
    }

    public static void sort1(int[] nums){
        sort1(nums,0, nums.length-1);
    }

    public static void sort1(int[] nums,int L,int R){
        if (L < R){
            int[] p = partition1(nums, L, R);
            sort1(nums,L,p[0]);
            sort1(nums,p[1],R);
        }
    }

    /**
     * 分割数组
     * @param nums 数组
     * @param L 左起始坐标
     * @param R 右结束坐标
     * @return 返回基准值的边界
     */
    public static int[] partition1(int[] nums,int L,int R){
        //存放边界
        int[] res = new int[2];
        //特殊情况
        if (L==R){
            res[0] = L;
            res[1] = R;
            return res;
        }
        //基准值
        int standard = nums[(L+R)/2];
        //荷兰国旗问题，左范围，右范围
        int lIndex = L-1;
        int rIndex = R+1;
        //进行分割
        while (L < rIndex){
            if (nums[L] < standard){
                MyUtils.swap(nums, L++, ++lIndex);
            }else if (nums[L] == standard){
                L++;
            }else if (nums[L] > standard){
                MyUtils.swap(nums, L, --rIndex);
            }

        }

        res[0] = lIndex;
        res[1] = rIndex;

        return res;
    }
}

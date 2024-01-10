package utils;

import day03.BinaryTree;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 * @author zjx
 * @Date 2023-12-26 22:32:22
 * @Desc : 自定义的一些工具
 */
public class MyUtils {
    /**
     * 创建一个数组
     * @param size 数组大小
     * @param min 最小值
     * @param max 最大值
     */
    public static int[] createArr(int size,int min,int max){
        if (size <= 0 ){
            return null;
        }
        //确定范围，如果不加1，两边会有一个取不到，因为Math.random()是[0,1)
        int range = max - min + 1;

        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            // 加 min 就使范围从 [0,max-min] --> [min.max]
            res[i] = (int) (Math.random()*range)+min;
        }
        return res;
    }

    /**
     * 交换数组中两个位置的值
     * @param nums 数组
     * @param i 位置i
     * @param j 位置j
     */
    public static void swap(int[] nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 测试数组是否为升序
     * @param arr 数组
     * @return false:不是升序数组     true：升序数组
     */
    public static boolean testArrSort(int[] arr){
        if (arr != null){
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j+1]){
                    System.out.println("有错误产生");
                    System.out.println("j = " + j);
                    System.out.println("arr["+j+"]" + " = "+arr[j] +"\tarr["+(j+1)+"]" + " = "+arr[j+1]);
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 获取数组的最大值
     * @param nums 数组
     * @return 最大值
     */
    public static int getMax(int[] nums){
        int res = nums[0];

        for (int num : nums) {
            if (num > res) {
                res = num;
            }
        }

        return res;
    }

    /**
     * 创建一个打乱顺序的数组,数组中的值为 0-size-1，每个数只出现一次
     * @param size 数组大小
     * @return 乱序数组
     */
    public static int[] createShuffledArray(int size){
        int[] ints = new int[size];
        for (int i = 0; i < size; i++) {
            ints[i] = i;
        }

        shuffleArray(ints);

        return ints;
    }

    /**
     * 打乱数组顺序
     */
    public static void shuffleArray(int[] nums){
        for (int i = 0; i < nums.length; i++) {
            Random random = new Random();
            swap(nums,i,random.nextInt(i, nums.length));
        }
    }

    /**
     * 向 map 中添加元素
     */
    public static void mapAddValue(Map<Integer,Integer> map,Integer key,Integer value ){
        if (map.containsKey(key)){
            map.put(key,map.get(key) + value);
        }else {
            map.put(key,value);
        }
    }

    /**
     * 判断两个数据是否相等
     */
    public static boolean arryIsEqual(int[] arr1,int[] arr2){
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

    /**
     * 获取以该节点为头节点的树的深度
     *
     * @param node 节点
     * @return 深度
     */
    public static int getDepthOfNode(Node node){
        //base case
        if (node == null){
            return 0;
        }

        if (node.right == null && node.left == null){
            return 1;
        }

        int leftDepth = getDepthOfNode(node.left);
        int rightDepth = getDepthOfNode(node.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }

    /**
     * 判断 n 是否为 x 的整次幂
     */
    public static boolean isPowerOfX(int x,int n) {

        // 如果是非正数，不是 x 的整数次幂
        if (n <= 0) {
            return false;
        }

        // 判断是否只有一个位是1
        return (Integer.toString(n, x).matches("^10*$"));
    }


}

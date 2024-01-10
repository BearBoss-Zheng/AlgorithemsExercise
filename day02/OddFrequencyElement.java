package day02;

import utils.MyUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author zjx
 * @Date 2024-01-08 19:53:00
 * @Desc：
 * 【题目】：
 * 1.数组中，有一个数组值出现了奇数次，其他数字只出现了偶数次，请找出这个奇数次的数字
 * 2.数组中。有两个数组值出现了奇数次，其他数字只出现了偶数次，请找出这两个奇数次的数字
 * 【思路】：
 * 1.由于 0 ^ n = n；n ^ n = 0
 * 2.针对问题（1），用 0 亦或数组内所有的数，最后得到的结果，就是 答案
 * 3.针对问题（2），使用步骤2可以得到 a ^ b；只需要得到这个数最后一位为1的数（a，b在该位上一个为1，一个为0），
 *   然后将数组中的数字分为两组，该位为 0 和 1 两组，然后分别用 0 进行亦或，就能分别得到 a 、 b两个结果
 */
public class OddFrequencyElement {
    public static void main(String[] args) {

        for (int i = 0; i < 200000; i++) {
            if (!(testTwo() && testOne())){
                System.out.println("结果有误");
                return;
            }
        }

        System.out.println("perfect!");
    }

    /**
     * 找出出现奇数次的一个数
     * 查找这个数
     * @param nums 数组
     * @return 出现奇数次的数
     */
    public static int findOne(int[] nums){
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }

        return res;
    }

    /**
     * 找出出现奇数次的两个数
     * 查找这两个数
     * @param nums 数组
     * @return 包含两个出现奇数次的数的数组
     */
    public static int[] findTwo(int[] nums) {
        int xorResult = 0;

        // 计算数组所有元素的异或结果，结果是两个出现奇数次的数的异或值
        for (int num : nums) {
            xorResult ^= num;
        }

        // 找到异或结果中最右边的为1的位，用于分割两个出现奇数次的数
        int rightmostSetBit = xorResult & -xorResult;

        // 根据这个位进行分组异或
        int group1 = 0, group2 = 0;
        for (int num : nums) {
            if ((num & rightmostSetBit) == 0) {
                // 该位为0的分组
                group1 ^= num;
            } else {
                // 该位为1的分组
                group2 ^= num;
            }
        }

        return new int[]{group1, group2};
    }

    /**
     * 对数器(测试 findOne 方法)
     */
    public static boolean testOne(){
        Random random = new Random();
        //数组打大小为[50,100)
        int size = random.nextInt(50,100);

        //创建一个打乱的数组
        int[] shuffledArray = MyUtils.createShuffledArray(size);
        //存放数据的数组
        int[] res = new int[size];

        //记录已创建数的数量
        int num = 0;
        //存放数字的表，每个数字有几个
        Map<Integer,Integer> table = new HashMap<>();

        //往 res 数组中填放数据
        while (num < size && num + 1 != size){
            //生成偶数个数
            Integer n = random.nextInt(10) * 2;
            while (!(num + n <= size) || n <= 0){
                n = random.nextInt(10)*2;
            }

            //偶数个value
            int value = random.nextInt(1,50);
            for (int i = num; i < n+num; i++) {
                res[shuffledArray[i]] = value;
            }

            //现在已经存放的个数
            num += n;
            //统计表
            MyUtils.mapAddValue(table,value,n);

        }

        int oddNum = 0;

        //res 当中所有的数都是出现了偶数次，此时只要增加一个数就行了
        if (num == size){
            int[] res1 = new int[size+1];
            System.arraycopy(res,0,res1,0,res.length);
            res1[size] = random.nextInt(100);
            oddNum = res1[size];
            res = res1;
            //增加的数要填进表
            MyUtils.mapAddValue(table,res1[size],1);

        }else {
            //有一个空格的话，就填上这个空格，该位置的数就是出现奇数次的数
            res[shuffledArray[size-1]] = random.nextInt(100);
            oddNum = res[shuffledArray[size-1]];

            MyUtils.mapAddValue(table,res[shuffledArray[size-1]],1);
        }

        int find = findOne(res);
        if (find != oddNum){
            System.out.println("findOne() 的结果为 : "+ find);
            System.out.println("结果理应为 ： " + oddNum);
            System.out.println(table);
            return false;
        }

        return true;

    }

    /**
     * 对数器 （测试 findTwo 方法）
     */
    public static boolean testTwo(){
        Random random = new Random();
        int size = random.nextInt(10,1000);

        //创建乱序数组，用于随即存放数据
        int[] shuffledArray = MyUtils.createShuffledArray(size);
        //存放数据的数组
        int[] nums = new int[size];
        //统计表
        Map<Integer,Integer> table = new HashMap<>();
        //记录已存放的数量
        int num = 0;

        //还剩多少数据的时候退出
        int bound = random.nextInt(20) * 2;
        while (bound > size || bound <= 4){
            bound = random.nextInt(20) * 2;
        }

        //存放数据
        while (num < size-bound && num+1 != size-bound){

            //生成偶数个数
            Integer n = random.nextInt(10) * 2;
            while (!(num + n <= size-bound) || n <= 0){
                n = random.nextInt(10)*2;
            }

            //偶数个value
            int value = random.nextInt(1,50);
            for (int i = num; i < n+num; i++) {
                nums[shuffledArray[i]] = value;
            }

            //现在已经存放的个数
            num += n;
            //统计表更新
            MyUtils.mapAddValue(table,value,n);

        }

        //生成两个出现奇数次的数
        int[] oddNum = new int[2];

        int odd1 = random.nextInt();
        int odd2 = random.nextInt();
        while (odd1 == odd2){
            odd2 = random.nextInt();
        }
        oddNum[0] = odd1;
        oddNum[1] = odd2;

        //nums 还剩偶数个空位
        if ((size-num)%2 == 0){

            int time1 = random.nextInt(1,bound/2)*2 -1;
            int time2 = bound - time1;

            for (int i = 0; i < time1; i++) {
                nums[shuffledArray[size-1-i]] = odd1;
            }

            for (int i = 0; i < time2; i++) {
                nums[shuffledArray[size-1-time1-i]] = odd2;
            }

            //增加的数要填进表
            MyUtils.mapAddValue(table,odd1,time1);
            MyUtils.mapAddValue(table,odd2,time2);

        }else {
            int[] num2 = new int[size +1];
            //有奇数个空格的话，先填上空余的数量
            int time1 = random.nextInt(1,bound/2)*2-1;
            int time2 = (size - num) - time1;

            for (int i = 0; i < time1; i++) {
                nums[shuffledArray[size-1-i]] = odd1;
            }

            for (int i = 0; i < time2; i++) {
                nums[shuffledArray[size-1-time1-i]] = odd2;
            }

            System.arraycopy(nums,0,num2,0,nums.length);
            num2[size] = odd2;

            MyUtils.mapAddValue(table,odd1,time1);
            MyUtils.mapAddValue(table,odd2,time2+1);

            nums = num2;
        }

        int[] find = findTwo(nums);
        if (!MyUtils.arryIsEqual(find,oddNum)){
            System.out.println("findOne() 的结果为 : "+ Arrays.toString(find));
            System.out.println("结果理应为 ： " + Arrays.toString(oddNum));
            System.out.println(table);
            return false;
        }

        return true;



    }



}

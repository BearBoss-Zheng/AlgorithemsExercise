package day04;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author zjx
 * @Date 2024-01-11 23:13:35
 * @Desc
 * KMP算法
 * KMP算法（Knuth-Morris-Pratt算法）是一种用于在文本串中查找子串的高效字符串匹配算法。
 * 它的主要思想是利用已经匹配过的信息，尽量减少不必要的字符比较。
 * KMP算法的核心是构建一个部分匹配表（Partial Match Table），简称PMTable或者Next数组。
 * 这个表告诉我们，在匹配过程中如果出现不匹配的情况，应该将模式串向右移动多少位，而不是简单地
 * 将模式串移动一位。
 * 具体步骤如下：
 * 1.构建部分匹配表：
 * 从模式串的第一个字符开始，对于每个位置，计算其前缀和后缀的最长公共部分长度。
 * 将这个长度填入部分匹配表中对应位置。
 * 2.在匹配时使用部分匹配表：
 * 当发生不匹配时，根据部分匹配表的值，移动模式串，使得模式串的某个前缀和文本串的当前位置对齐。
 * 这样，KMP算法避免了在文本串中的每个可能位置都重新比较所有字符，提高了匹配的效率。
 *
 * 【题目】
 * 字符串str1和str2，str1是否包含str2，如果包含返回str2在str1中开始的位置。
 * 如何做到时间复杂度O(N)完成
 */
public class KnuthMorrisPratt {

    public static void main(String[] args) {
//        String str = "ABABCABAB";
//        int[] array = getNextArray(str.toCharArray());
//        System.out.println(Arrays.toString(array));
        String str1 = "gaaabababcabcgabcdeabcdeabcdef";
        String str2 = "abcdeabcdef";
        System.out.printf("str1包含str2，从%d开始", indexOfSubstring(str1, str2));
    }

    public static int indexOfSubstring(String str1,String str2){
        //base case
        if (str1 == null || str2 == null || str1.length() < 1 || str1.length() < str2.length()){
            return -1;
        }

        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[] subNextArr = getNextArray(chars2);
        int index1 = 0;//str1 的指标
        int index2 = 0;//str2 的指标

        while (index1 < str1.length() && index2 <str2.length()){
            if (chars1[index1] == chars2[index2]){
                index1++;
                index2++;
            }else if (subNextArr[index2] >= 0){
                //注意这里的条件
                //这么做出为了防止 -1出现，
                index2 = subNextArr[index2];
            }else {
                index1++;
            }
        }

        return index2 == str2.length() ? index1 - index2 : -1;


    }

    /**
     * KMP算法的核心是构建一个部分匹配表（Partial Match Table），简称PMTable或者Next数组。
     * 对于每个位置，计算其前缀和后缀的最长公共部分长度。
     * 注意前缀和后缀，都是从前往后的，不要把后缀当作从后往前。
     * 例如：假设模式串为 ABABCABAB。
     * lps[0] = -1：0个字符没有真正的前缀和后缀。
     * lps[1] = 0：子串 "A" 的前后缀为空，长度为 0。
     * lps[2] = 0：子串 "AB" 的前后缀为空，长度为 0。
     * lps[3] = 1：子串 "ABA" 的前后缀为 "A"，共同的前缀和后缀长度为 1。
     * lps[4] = 2：子串 "ABAB" 的前后缀为 "AB"，共同的前缀和后缀长度为 2。
     * lps[5] = 0：子串 "ABABC" 的前后缀为空，长度为 0。
     * lps[6] = 1：子串 "ABABCA" 的前后缀为 "A"，共同的前缀和后缀长度为 1。
     * lps[7] = 2：子串 "ABABCAB" 的前后缀为 "AB"，共同的前缀和后缀长度为 2。
     * lps[8] = 3：子串 "ABABCABA" 的前后缀为 "ABA"，共同的前缀和后缀长度为 3。
     *
     * 可以使用动态规划的方式进行表格填写
     *
     * @param chs 字符集
     * @return 部分匹配表（Partial Match Table），简称PMTable或者Next数组。
     */
    public static int[] getNextArray(char[] chs){
        if (chs == null || chs.length == 0){
            return null;
        }

        if (chs.length == 1){
            return new int[]{-1};
        }


        int[] next = new int[chs.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;//next数组坐标哦
        int index = 0;//哪个位置的字符和 i-1 位置比，同时也是res[i-1]的值


        //有一张我画的图，有助于理解，见./pic/kmp.jpg
        while (i < next.length){
            /*
                这里可以这么理解，前面的最长公共前后缀已经知道了
                如果新扩展出来的字符，和原来最长公共前后缀长度后面的那个字符相等，
                那么现在的长度，只要在原先的基础上 +1 即可
             */
            if (chs[index] == chs[i-1]){
                next[i++] = ++index;
            }else if (index > 0){
                index = next[index];
            }else {
                /*
                    这里可以这么理解：
                    已经到了最后一个字符和第一个字符匹配的地步了，
                    但还是不相等，所以只能为0。
                    因为如果相等的话，在第一个if条件的时候，就已经过去了，根本不会到这里来
                 */
                next[i++] = 0;
            }
        }

        return next;
    }

}

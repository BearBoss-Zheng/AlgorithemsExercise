package day05;

/**
 * @author zjx
 * @Date 2024-01-12 16:27:53
 * @Desc:
 * Manacher算法
 * 【介绍】
 * Manacher算法是一种用于查找最长回文子串的线性时间复杂度算法。
 * 回文串是指正着读和倒着读都相同的字符串片段。Manacher算法的主要优势在于其时间复杂度为O(n)，
 * 其中n是字符串的长度。
 * 【核心思想】
 * 该算法的核心思想是利用回文串的对称性，避免重复计算。它通过在字符串中插入特殊字符（通常是#）
 * 来处理奇偶长度的回文串，然后利用已知的回文串信息来加速寻找新的回文串。
 */
public class Manacher {

    public static void main(String[] args) {
        String str = "aaafaskjhgfakljbabccbaaavashjkg";
        System.out.println(maxLcpsLength(str));
    }

    /**
     * 寻找最长会问字串的长度
     * 步骤如下：
     * abc -> #a#b#c#
     * cur 表示当前的节点
     * pre 表示前一个节点，L表示前一个节点最长回文字串的左边界，R为右边界
     * sym(symmetric)表示 以pre为原点，cur的对成点
     * 利用回文字串的对成性，可以简化算法如下：
     * 1.cur > R
     *      直接暴力外扩，往外面一步步扩张，找回文串，最原始的方法
     * 2.cur <= R
     *      ① sym的左边界在 L 内，那么pal[cur] = pal[sym]
     *      ② sym的左边边界就是 L ，那么cur可以往外再找找，因为pal[cur] 有可能突破R
     *      ③ sym的左边界超过了 L ，那么pal[cur] = (R-cur)*2 +1 、
     *          ./pic/manacher.jpg 可进一步帮助理解 ③
     * @param str
     * @return
     */
    public static int findLongestPalindromeLength(String str){
        if (str == null || str.length() == 0){
            return 0;
        }

        if (str.length() == 1){
            return 1;
        }


        //将字符串进行处理，abc -> #a#b#c#
        char[] chs = manacherString(str);

        int[] pal = new int[chs.length];
        //为了方便后续结果的计算，pal[]放置的回文半径
        pal[0] = 0;
        int pre = 0;
        int L = 0;
        int R = 0;
        int sym = 0;
        int cur = 1;
        int maxLength = 1;

        //计算各个位置的回文长度
        while (cur < chs.length){
            //对称点
            sym = pre - (cur - pre);

            if (cur > R){
                //情况1
                //暴力外扩
                int[] info = getMaxPalindromeAtPosition(chs, cur, cur);
                pal[cur] = info[0];
                R = info[1];
                L = cur -(R - cur);
                //处理下一个节点
                pre = cur++;
            }else {
                //情况2

                //对称点的左边界
                int sL = sym - pal[sym];
                if (sL > L){
                    //情况2-①
                    pal[cur++] = pal[sym];
                }else if (sL == L){
                    //情况2-②
                    int[] info = getMaxPalindromeAtPosition(chs,cur,R);
                    if (R < info[1]){
                        pre = cur;
                        R = info[1];
                        L = cur -(R - cur);
                    }
                    pal[cur++] = info[0];
                }else {
                    //情况2-③
                    pal[cur] = R - cur;
                    cur++;
                }
            }

            /*if (cur % 2 == 0){
                maxLength = Math.max(maxLength,pal[cur-1]/2 * 2 +1);
            }else {
                maxLength = Math.max(maxLength,pal[cur-1]);
            }*/

            /*
                简化
                #a#a# pal = 2,length=2pal+1-(pal/2*2+1) = pal
                #a# pal = 1,length = 2pal+1-((pal-1)/2)*2+1 = pal
             */
            maxLength = Math.max(maxLength,pal[cur-1]);

        }

        return maxLength;



    }

    /**
     * 优化写法，代码更加简洁
     */
    public static int maxLcpsLength(String str){
        if (str == null || str.length() == 0){
            return 0;
        }

        if (str.length() == 1){
            return 1;
        }


        //将字符串进行处理，abc -> #a#b#c#
        char[] chs = manacherString(str);

        int[] pal = new int[chs.length];
        //为了方便后续结果的计算，pal[]放置的回文半径
        pal[0] = 0;
        int pre = 0;
        int R = 0;
        int sym = 0;
        int cur = 1;
        int maxLength = 1;

        //计算各个位置的回文长度
        while (cur < chs.length){
            sym = pre - (cur -pre);

            pal[cur] = cur < R ? pal[sym] : 1;

            while (cur + pal[cur] < chs.length && cur - pal[cur] > -1
                        &&
                    chs[cur + pal[cur]] == chs[cur - pal[cur]]){
                    pal[cur]++;
            }

            if (cur + pal[cur] - 1 > R ){
                R = cur + pal[cur];
                pre = cur;
            }

            maxLength = Math.max(maxLength,pal[cur]);

            cur++;
        }

        /*
            回文最长的无非就是奇数位、或者偶数位
            回文长度 = 总长度 - “#”数量
            如果是以“#”为中心的回文数，回文半径一定是奇数，#、#a#a#，回文半径分别为1，3
            如果不是以“#”为中心的回文数，回文半径一定是偶数，#a#，#a#a#a#，回文半径分别为2，4
            奇数：(pal-1)*2+1-pal = pal - 1
            偶数：(pal-1)*2+1-pal = pal - 1
         */
        return maxLength -1;
    }

    /**
     * 获取当前位置上的最大回文的长度（暴力扩张）
     *
     * @param chs      字符串
     * @param position 当前位置
     * @param R        已经求得的回文数的右边界
     * @return {回文长度，右边界}
     */
    public static int[] getMaxPalindromeAtPosition(char[] chs, int position, int R) {
        int[] res = new int[2];
        res[0] = R - position;
        res[1] = R;
        for (int i = R - position + 1; i < chs.length - position; i++) {
            if (position - i >= 0 && chs[position + i] == chs[position - i]) {
                res[0]++;
                res[1]++;
            }

            if (position - i < 0 || chs[position + i] != chs[position - i]) {
                break;
            }
        }

        return res;
    }

    /**
     * 将 abc ->  "#a#b#c#"
     */
    public static char[] manacherString(String s){
        char[] charArray = s.toCharArray();
        char[] res = new char[s.length() * 2 + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArray[i/2];
        }
        return res;
    }

    //review
    public static int getMaxPalindrome(String str){
        if (str == null || str.length() == 0){
            return 0;
        }

        char[] chars = manacherString(str);
        int[] pal = new int[chars.length];
        int maxLength = 1;
        pal[0] = 1;
        int pre = 0;
        int cur = 1;
        int R = 1;

        for (cur = 1; cur < chars.length; cur++) {
            //对称点pre - (cur-pre)
            pal[cur] = cur < R ? pal[2*pre-cur] : 1;

            while (cur + pal[cur] < chars.length && cur - pal[cur] > -1
                                &&
                    chars[cur+pal[cur]] == chars[cur-pal[cur]]){

                    pal[cur]++;

            }

            if (cur + pal[cur] -1 > R){
                R = cur + pal[cur] -1;
                pre = cur;
            }

            maxLength = Math.max(maxLength,pal[cur]);
        }

        return maxLength-1;

    }
}

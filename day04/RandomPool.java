package day04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * @author zjx
 * @Date 2024-01-11 14:36:29
 * @Desc
 * 【题目】
 * 设计一种结构，再该结构中有如下三个功能：
 * insert(key) : 将某个key加入到该结构，做到不重复加入
 * delete(key) : 将原本在结构中的某个key移除
 * getRandom() : 等概率返回结构中的任何一个key
 */
public class RandomPool<K,V> {
    /**
     * 最初的方案，有一定的缺陷，代码比较冗杂：
     * 1.泛型 V 没有实际用处
     * 2.成员变量 pool 也没什么作用
     * 优点在于整体思想不错，基于这个思想，下面进行优化
     */
    public static class Pool1<K,V>{
        //用于存储实际数据
        HashMap<K,V> pool;
        //主要用于随机取key
        HashMap<K,Integer> searchTable;
        HashMap<Integer,K> helpSearchTable;
        int size;


        public Pool1() {
            pool = new HashMap<K,V>();
            searchTable = new HashMap<K,Integer>();
            helpSearchTable = new HashMap<Integer,K>();
            size = 0;
        }

        public void insert(K k){
            if (!pool.containsKey(k)){
                //如果k不存在，那么直接放入即可
                size++;
                searchTable.put(k,size);
                helpSearchTable.put(size,k);
            }
            //如果k之存在，那么现在就是更新
            //就不需要更新searchTable和size
            //只需要更新 pool
            pool.put(k,null);
        }

        public void delete(K k){
            if (!pool.containsKey(k)){
                return;
            }else {
                pool.remove(k);
                size--;
                int index = searchTable.get(k);
                if (index != searchTable.size()){
                    K lastKey = helpSearchTable.get(searchTable.size());
                /*
                将lastKey 标号改为 被删除的节点的标号，使得标号连续
                    k1  1
                    k2  2
                    k3  3
                    k4  4
                    加入删除k2，那么就会变成
                    k1  1
                    k4  2
                    k3  3
                 */
                    searchTable.put(lastKey,index);
                    searchTable.remove(k);
                    //对helpSearchTable进行更新
                    helpSearchTable.put(index,lastKey);
                    helpSearchTable.remove(size);
                }else {
                    //如果删除就是最后一个
                    //那么会直接删除即可
                    searchTable.remove(k);
                    helpSearchTable.remove(index);
                    size--;
                }

            }

        }


        /**
         * 随机提供一个 k
         * 时间复杂度为 o(1)。
         * 在searchTable 当中，所有的元素都是利用 1-size 标好的
         * 所以随机选一个就好了
         *
         */
        public K getRandom(){
            Random random = new Random();
            int randomIndex = random.nextInt(1, size + 1);
            return helpSearchTable.get(randomIndex);
        }

    }

    /**
     * 优化方案
     */
    public static class Pool2<K>{
        private HashMap<K,Integer> keyIntegerMap;
        private HashMap<Integer,K> indexKeyMap;
        private int size;

        private Pool2(){
            this.keyIntegerMap = new HashMap<K,Integer>();
            this.indexKeyMap = new HashMap<Integer,K>();
            this.size = 0;
        }

        public void insert(K k){
            if (!keyIntegerMap.containsKey(k)){
                keyIntegerMap.put(k,this.size);
                indexKeyMap.put(this.size++,k);
            }
        }

        public void delete(K k){
            if (!keyIntegerMap.containsKey(k)){
                return;
            }

            int lastIndex = --size;
            int deleteIndex = this.keyIntegerMap.get(k);
            /*if (lastIndex == deleteIndex){
                keyIntegerMap.remove(k);
                indexKeyMap.remove(lastIndex);
            }else {
                K lastK = indexKeyMap.get(lastIndex);
                keyIntegerMap.put(lastK,deleteIndex);
                keyIntegerMap.remove(k);

                indexKeyMap.put(deleteIndex,lastK);
                indexKeyMap.remove(lastIndex);
            }*/
            //可以简化为
            K lastK = this.indexKeyMap.get(lastIndex);
            this.keyIntegerMap.put(lastK,deleteIndex);
            this.keyIntegerMap.remove(k);

            this.indexKeyMap.put(deleteIndex,lastK);
            this.indexKeyMap.remove(lastIndex);

        }

        public K getRandom(){
            Random random = new Random();
            //[0, size)
            int randomIndex = random.nextInt(size);
            return indexKeyMap.get(randomIndex);
        }
    }


}

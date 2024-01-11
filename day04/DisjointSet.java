package day04;

import java.util.*;

/**
 * @author zjx
 * @Date 2024-01-11 18:15:13
 * @Desc:
 * 【概念】
 * 并查集结构：“Disjoint-Set Data Structure”，常简称为 "Disjoint-Set"
 * 或 "Union-Find"。在这个数据结构中，主要支持两种操作：合并（Union）和查找（Find）。
 *
 */
public class DisjointSet {
    public static class Element<V>{
        V value;

        public Element(V value){
            this.value = value;
        }
    }

    public static class UnionFindSet<V>{
        //映射 V -> Element
        HashMap<V, Element<V>> elementMap;
        //key为子，value为父
        HashMap<Element<V>, Element<V>> parentMap;
        //每个元素所在集合的大小
        HashMap<Element<V>,Integer> sizeMap;

        //构造器
        public UnionFindSet(List<V> list){
            this.elementMap = new HashMap<>();
            this.parentMap = new HashMap<>();
            this.sizeMap = new HashMap<>();

            for (V v : list) {
                Element<V> e = new Element<>(v);
                elementMap.put(v,e);
                parentMap.put(e,e);
                sizeMap.put(e,1);
            }
        }

        // 查询集合的头节点
        public Element<V> getHead(Element<V> element){
            Deque<Element<V>> stack = new ArrayDeque<>();
            //父节点为自己的，就是头节点
            while (element != parentMap.get(element)){
                stack.add(element);
                element = parentMap.get(element);
            }

            //找到头节点之后，进行扁平化处理，将所有子节点，都指向头节点
            Element<V> head = element;

            while (!stack.isEmpty()){
                element = stack.pop();
                parentMap.put(element,head);
            }

            return head;

        }

        // 是否为同一集
        public boolean isSameSet(V a,V b){
            //集合元素当中是否存在这两个元素，存在才有查找的必要
            if (elementMap.containsKey(a) && elementMap.containsKey(b)){
                //看二者是否为同一父元素，只有父元素相同，才为同一并查集
                Element<V> aF = parentMap.get(elementMap.get(a));
                Element<V> bF = parentMap.get(elementMap.get(b));

                return aF == bF;
            }

            return false;
        }

        // 合并两个并查集
        public void union(V a,V b){
            if (elementMap.containsKey(a) && elementMap.containsKey(b)){
                Element<V> aF = getHead(elementMap.get(a));
                Element<V> bF = getHead(elementMap.get(b));
                //如果含有同一父类，表明在同一集合，不需要合并
                if (aF != bF){
                    Element<V> big = sizeMap.get(aF) >= sizeMap.get(bF) ? aF : bF;
                    Element<V> small = big == aF ? bF : aF;
                    //更新big和small
                    parentMap.put(small,big);
                    sizeMap.put(big,sizeMap.get(small) + sizeMap.get(big));
                    sizeMap.remove(small);
                }
            }
        }

        // 获取集合的大小
        public int getSetSize(V value) {
            Element<V> element = elementMap.get(value);
            return sizeMap.get(getHead(element));
        }

        // 获取集合的数量
        public int getSetCount(){
            return sizeMap.size();
        }
    }

    /**
     * 岛屿问题
     * 【题目】
     * 一个矩阵只有0和1两种值，每个位置都可以和自己的上、下、左、右四个位置相连，
     * 如果有一片1连在一起，这个部分叫做一个岛，求一个矩阵中有多少个岛。
     * 【举例】
     * 001010
     * 111010
     * 100100
     * 000000
     * 这个矩阵当中有3个岛
     *
     * 【思路分析】
     * 利用并查集结构
     * 并查集的本质，其实就是将一个个相关的元素聚合到一个集合当中，
     * 1. 针对本题，一个集合就是一个岛屿。所以完全可以忽略所有的0，值提取出 1 .
     *    但是 “1” 毕竟只是一个数据，所以要进行特殊的处理，使得不同位置上的 1 有不同的表示，避免重复
     *    因此进行如下处理：column * row .length + index of row
     *    提取出来的 1 全家加入 List 集合当中，方便后续的并查集处理
     * 2. 创建并查集
     * 3. 按岛屿的性质，进行集合的合并，即相连在一起的 “1” 属于同一个岛屿，设当前值为 curValue
     *    要合并的，其实就是上、下、左、右四个值
     *    上：curValue - matrix[0].length
     *    下：curValue + matrix[0].length
     *    左：curValue - 1
     *    右：curValue + 1
     * @param matrix 矩阵
     * @return 岛屿数量
     */
    public static int getIslandCount(Integer[][] matrix){
        if (matrix == null){
            return 0;
        }

        //将 1 分离出来
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1){
                    list.add(i * matrix[0].length + j);
                }
            }
        }

        //创建并查集
        UnionFindSet<Integer> unionFindSet = new UnionFindSet<Integer>(list);


        //将相连的 1 合并成一个集合
        for (Integer curValue : list) {
            //合并上
            unionFindSet.union(curValue,curValue - matrix[0].length);
            //合并下
            unionFindSet.union(curValue,curValue + matrix[0].length);
            //合并左
            unionFindSet.union(curValue,curValue - 1);
            //合并右
            unionFindSet.union(curValue,curValue + 1);
        }

        return unionFindSet.getSetCount();

    }

    public static void main(String[] args) {
        Integer[][] matrix = {
                {0,0,1,0,1,0},
                {1,1,1,0,1,0},
                {1,0,0,1,0,0},
                {0,0,0,0,0,0}
        };


        System.out.printf("一共有：%d个岛屿\n", getIslandCount(matrix));

    }
}

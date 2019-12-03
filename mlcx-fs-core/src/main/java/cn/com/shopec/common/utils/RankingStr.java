package cn.com.shopec.common.utils;

import java.util.List;

public class RankingStr{

    public static void main(String[] args) {
        String [] ss ={"orderNum04","orderNum02","orderNum03","orderNum01"};
        RankingStr.strSort(ss);
        printArr(ss);
    }
    /**
     * 
     * 对字符串进行由小到大排序
     * @param str    String[] 需要排序的字符串数组
     */
    public static void strSort(String[] str){
        for (int i = 0; i < str.length; i++) {
            for (int j = i+1; j < str.length; j++) {
                if(str[i].compareTo(str[j])>0){    //对象排序用camparTo方法
                    swap(str,i,j);
                }
            }
        }
        
    }
    /**
     * 交换两个元素的位置的方法
     * @param strSort    需要交换元素的数组    
     * @param i    索引i
     * @param j 索引j
     */
    private static void swap(String[] strSort, int i, int j) {
        String t = strSort[i];
        strSort[i] = strSort[j];
        strSort[j] = t;
    }
    /**
     * 打印字符串数组
     * @param str
     */
    private static void printArr(String[] str) {
        for (int i = 0; i < str.length; i++) {
            System.out.print(str[i]+"\t");
        }
        System.out.println();
    }
}
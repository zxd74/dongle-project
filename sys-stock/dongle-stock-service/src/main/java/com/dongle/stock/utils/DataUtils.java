package com.dongle.stock.utils;

import java.util.Collection;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/9 009 11:05
 */
public class DataUtils {
    /**
     * Object判空校验
     * @param obj Object
     * @return boolean 为空return true，否则return false
     */
    public static boolean isEmpty(Object obj){
        return obj == null;
    }

    /**
     * 字符串判空校验
     * @param str 待校验字符串
     * @return boolean 为空return true，否则return false
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 集合数据判空
     * @param collection 待校验集合对象
     * @return boolean， 为空return true，否则return false
     */
    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    /**
     * 差值百分比
     * @param d1 比较数
     * @param d2 被比较数
     * @return 百分比
     */
    public static double changeRate(double d1,double d2){
        return change(d1,d2) * 100;
    }

    /**
     * 差值占比：比较两个数据差异率，d1与d2的差相较于d1的差值比
     * @param d1  比较数
     * @param d2 被比较数
     * @return 若d2==0，则返回1d,否则返回差值比
     */
    public static double change(double d1,double d2){
        if (d2 == 0d) return 1d;
        return (d1 - d2) / d2;
    }
}

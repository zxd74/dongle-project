package com.dongle.adx.common.util;

import java.util.Collection;

/**
 * @author : 郑晓东
 * @version : v1.0
 * @description ：集合工具类
 * @since : 2019-09-29 18:24
 */
public class CollectionUtils {

    /**
     * 集合不为空判断，不为空为true，为空为false
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return collection !=null && !collection.isEmpty();
    }

    /**
     * 集合为空,为空为true，不为空为false
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection){
        return collection ==null && collection.isEmpty();
    }

    /**
     * 一个集合包含另外一个集合中任意一个元素
     *      存在，为true；不存在为false
     * @param collection
     * @param items
     * @return
     */
    public static boolean containsAny(Collection<?> collection, Collection<?> items){
        for (Object item : items) {
            if (collection.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 一个集合中包含另外一个集合所有元素
     *      包含,为true
     *      有一个不包含，为false
     * @param collection
     * @param items
     * @return
     */
    public static boolean containsAll(Collection<?> collection, Collection<?> items) {
        for (Object item : items) {
            if (!collection.contains(item)) {
                return false;
            }
        }
        return true;
    }
}

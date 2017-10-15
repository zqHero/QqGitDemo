package com.hero.zhaoq.qqgitdemo.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author: zhaoqiang
 * date:2017/09/21 / 14:28
 * zhaoqiang:zhaoq_hero@163.com
 */

public class CollectionsUtils {

    /**
     * 判断集合   是否为null
     * @param selectFiles
     * @return
     */
    public static <T> boolean isNoEmpty(List<T> selectFiles) {
        if (selectFiles!=null && selectFiles.size()!= 0)
            return true;
        return false;
    }


    /**
     * 从  一个map  集合中  获取    所有值的ArrayList
     * @return
     */
    public static <I,T> List<T> getValueListFromMap(Map<I, T> tMap) {
        List<T> users = null;
        if (tMap != null){
            users = new ArrayList<>();
        }
        for (Map.Entry<I,T> item:tMap.entrySet()){
            users.add(item.getValue());
        }
        return users;
    }


    /**
     * 从  一个map  集合中   获取    所有键的ArrayList
     * @return
     */
    public static <I,T> List<I> getKeysListFromMap(Map<I, T> tMap) {
        ArrayList<I> users = null;
        if (tMap != null){
            users = new ArrayList<>();
        }
        for (Map.Entry<I,T> map:tMap.entrySet()){
            users.add(map.getKey());
        }
        return users;
    }

    /**
     * 字符串 数组 转换为  list:
     * @param stringArray
     * @return
     */
    public static <T> ArrayList<T> Strs2AList(T[] stringArray) {
        ArrayList<T> lists = null;
        if (stringArray !=null){
            lists = new ArrayList<>();

            for (T t:stringArray) {
                lists.add(t);
            }

            return lists;
        }
        return null;
    }
}

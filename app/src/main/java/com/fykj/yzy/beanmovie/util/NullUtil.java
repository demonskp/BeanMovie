package com.fykj.yzy.beanmovie.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullUtil {

    public static boolean isNull(String str) {
        if (null == str || 0 == str.length()|| str.trim().length() == 0 || "null".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNull(ArrayList<T> sourceList) {
        if (null != sourceList && !sourceList.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public static <T> boolean isNull(T[] sourceList) {
        if (null != sourceList && sourceList.length>0 ) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public static <T> boolean isNull(List<T> list) {
        if (null != list && !list.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * 判断map 是否为空
     * @param <T>
     */
    public static <T> boolean isNull(Map<T, T> map) {
        if (map != null) {
            if (map.size() != 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * 判断map 是否为空
     * @param <T>
     */
    public static <T> boolean isNull(HashMap<T, T> map) {
        if (map != null) {
            if (map.size() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串数组是否为空
     */
    public static boolean isNull(String[] string) {
        if (string != null) {
            if (string.length != 0) {
                return false;
            }
        }
        return true;
    }
   
}

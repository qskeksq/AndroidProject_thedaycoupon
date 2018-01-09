package com.thedaycoupon.util;

/**
 * Created by mac on 2017. 12. 13..
 */
public class DetailUtil {

    public static String splitInfo(String info, String prefix) {
        String result = "";
        String[] splitted = info.split("\\|");
        for (int i = 0; i < splitted.length; i++) {
            result += prefix;
            result += splitted[i];
            if (i != splitted.length - 1) {
                result += "\n";
            }
        }
        return result;
    }

    public static String appendPeriod(String start, String end) {
        return start + "~" + end;
    }

    public static boolean isEmpty(String str) {
        if (str != null && !str.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasLocation(double longitude, double latitude) {
        if (longitude != 0 && latitude != 0) {
            return true;
        } else {
            return false;
        }
    }

}
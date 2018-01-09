package com.thedaycoupon.util;

/**
 * Created by mac on 2017. 12. 14..
 */

public class RequestUtil {

    public static String makeDate(int year, int month, int day) {
        String date = "";
        date += year;
        month = month + 1;
        if (month < 10) {
            date += "-0" + month;
        } else {
            date += "-" + month;
        }
        if (day < 10) {
            date += "-0" + day;
        } else {
            date += "-" + day;
        }
        return date;
    }

    public static boolean isEmpty(String str) {
        if (str != null && !str.equals("")) {
            return false;
        } else {
            return true;
        }
    }

}

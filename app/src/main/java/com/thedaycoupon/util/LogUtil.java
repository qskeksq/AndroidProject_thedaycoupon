package com.thedaycoupon.util;

import android.util.Log;

import static com.thedaycoupon.BuildConfig.DEBUG;

/**
 * Created by mac on 2017. 12. 12..
 */

public class LogUtil {

    public static void d(String tag, String msg) {
        if(DEBUG) Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if(DEBUG) Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if(DEBUG) Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if(DEBUG) Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if(DEBUG) Log.d(tag, msg);
    }

}

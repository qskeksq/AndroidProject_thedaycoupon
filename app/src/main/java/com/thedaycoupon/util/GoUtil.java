package com.thedaycoupon.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by Administrator on 2017-11-11.
 */

public class GoUtil {

    public static <S> void startActivity(Context context, Class<S> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    public static <S> void startActivity(Context context, Class<S> className, String extra) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.GOUTIL_EXTRA_1, extra);
        context.startActivity(intent);
    }

    public static <S> void startActivity(Context context, Class<S> className, String[] extra) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.GOUTIL_EXTRA_1, extra);
        context.startActivity(intent);
    }

    public static <S> void startActivity(Context context, Class<S> className, int extra) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.GOUTIL_EXTRA_1, extra);
        context.startActivity(intent);
    }

    public static <S> void startActivity(Context context, Class<S> className, String extra1, String extra2) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.GOUTIL_EXTRA_1, extra1);
        intent.putExtra(Const.GOUTIL_EXTRA_2, extra2);
        context.startActivity(intent);
    }

    public static <S> void startActivity(Context context, Class<S> className, String extra1, int extra2) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.GOUTIL_EXTRA_1, extra1);
        intent.putExtra(Const.GOUTIL_EXTRA_2, extra2);
        context.startActivity(intent);
    }

    public static <S> void startActivity(Context context, Class<S> className, double extra1, double extra2) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.GOUTIL_EXTRA_1, extra1);
        intent.putExtra(Const.GOUTIL_EXTRA_2, extra2);
        context.startActivity(intent);
    }

    public static <S> void startActivityForResult(Activity activity, Class<S> className, int reqCode) {
        Intent intent = new Intent(activity, className);
        activity.startActivityForResult(intent, reqCode);
    }

    public static <S> void startActivityWithFlag(Context context, Class<S> className) {
        Intent intent = new Intent(context, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void goSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        activity.startActivityForResult(intent, Const.REQ_CODE_WIFI_SETTINGS);
    }

}

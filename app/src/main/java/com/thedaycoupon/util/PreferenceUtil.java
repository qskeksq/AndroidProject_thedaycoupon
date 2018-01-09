package com.thedaycoupon.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017-11-11.
 */

public class PreferenceUtil {

    private static final String PREFERENCE_KEY = "PREFERENCE_KEY";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getPreferenceEditor(Context context) {
        if (sp == null) {
            sp = getPreference(context);
        }
        return sp.edit();
    }

    public static void putString(Context context, String key, String value) {
        sp = getPreference(context);
        editor = getPreferenceEditor(context);
        editor.putString(key, value);
        editor.commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        sp = getPreference(context);
        editor = getPreferenceEditor(context);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putInt(Context context, String key, int value) {
        sp = getPreference(context);
        editor = getPreferenceEditor(context);
        editor.putInt(key, value);
        editor.commit();
    }

    public static void delete(Context context, String key) {
        sp = getPreference(context);
        editor = getPreferenceEditor(context);
        editor.remove(key);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        sp = getPreference(context);
        return sp.getString(key, "");
    }

    public static boolean getBoolean(Context context, String key) {
        sp = getPreference(context);
        return sp.getBoolean(key, false);
    }

    public static int getInt(Context context, String key) {
        sp = getPreference(context);
        return sp.getInt(key, 0);
    }
}

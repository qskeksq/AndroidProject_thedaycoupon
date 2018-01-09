package com.thedaycoupon.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017-11-17.
 */
public class StringUtil {

    public static void setTypefaceNanumL(Context context, TextView textView){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/NanumSquareL.ttf");
        textView.setTypeface(typeface);
    }

    public static void setTypefaceNanumR(Context context, TextView textView){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/NanumSquareR.ttf");
        textView.setTypeface(typeface);
    }

    public static void setTypefaceNanumB(Context context, TextView textView){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/NanumSquareB.ttf");
        textView.setTypeface(typeface);
    }

    public static String sdf(String date){
        return (date.substring(0, 10)).replace("-", ".");
    }

    public static String substring(String str, int begin){
        return str.substring(begin);
    }

    public static boolean isEmpty(String str){
        if(str != null && !str.equals("")){
            return false;
        } else {
            return true;
        }
    }

    /**
     * 이메일 유효값 체크
     */
    public static boolean isValidEmail(String email){
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    /**
     * 이름 체크
     */
    public static boolean isValidName(String name){

        boolean err = false;
        String regex = "^[가-힣a-zA-Z0-9]{2,12}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(name);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    /**
     * 비밀번호 유효값 체크
     */
    public static boolean isValidPassword(String password){
        boolean err = false;
        String regex = "^[a-zA-Z0-9]{6,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    /**
     * 2차 비밀번호 확인
     */
    public static boolean isValidSecondPassword(String first, String second){
        return first.equals(second);
    }


}

package com.thedaycoupon.activity;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.kakao.auth.KakaoSDK;
import com.thedaycoupon.adapter.KakaoSDKAdapter;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Administrator on 2017-11-26.
 */

public class GlobalApplication extends Application {

    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        obj = this;
        initKakao();
    }

    public static void initKakao(){
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        obj = null;
    }
}

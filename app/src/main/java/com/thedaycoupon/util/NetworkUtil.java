package com.thedaycoupon.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017-11-11.
 */

public class NetworkUtil {

    public static final String TAG = "NetworkUtil";

    private static final String NETWORK_STATE_WIFI = "WIFI";
    private static final String NETWORK_STATE_DATA = "MOBILE DATA";
    private static final String NETWORK_STATE_NONE = "NONE";

    /**
     * 네트워크 상태 확인
     */
    public static int checkNetworkStatus(Context context) {
        ConnectivityManager networkManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = networkManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                LogUtil.e(TAG, "wifi");
                return ConnectivityManager.TYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                LogUtil.e(TAG, "mobile");
                return ConnectivityManager.TYPE_MOBILE;
            }
        }
        LogUtil.e(TAG, "disconnected");
        NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        return 2;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager networkManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = networkManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
            return false;
        }
        return networkInfo.isConnected();
    }

    public static boolean isConnectedSimple(Context context) {
        ConnectivityManager networkManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = networkManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

}

package com.thedaycoupon.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;

import com.crashlytics.android.Crashlytics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 디바이스 정보 확인
 */
public class DeviceInfoUtil {

    /**
     * 기기 구분자
     */
    public static String getPhoneIdentifier(Context context) {

        String phoneNumber = getPhoneNumber(context);
        if (!phoneNumber.equals("") || phoneNumber != null) {
            return phoneNumber;
        }

        String deviceId = getDeviceId(context);
        if (!deviceId.equals("") || deviceId != null) {
            return deviceId;
        }
        NoticeUtil.makeToast(context, "기기 정보가 없습니다");
        return null;
    }

    /**
     * 전화번호
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();
        return phoneNumber;
    }

    /**
     * 기기번호
     */
    public static String getDeviceId(Context context) {
        // 1. DeviceId
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();

        if (deviceId != null && !deviceId.equals("")) {
            return deviceId;
        }

        // 2. android Id
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidId != null && !androidId.equals("")) {
            return androidId;
        }

        // 3. serial number
        String serial = "";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            serial = Build.SERIAL;
        }
        if (serial != null && !serial.equals("")) {
            return serial;
        }
        return null;
    }

    public static String createTempId() {
        String uuid = UUID.randomUUID().toString();
        long time = System.currentTimeMillis();
        return time + "_" + uuid;
    }

    /**
     * key-hash 값
     */
    public static String getKeyHash(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            Crashlytics.logException(e);
        }
        if (packageInfo == null)
            return null;
        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String msg = Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                return msg;
            } catch (NoSuchAlgorithmException e) {
                Crashlytics.logException(e);
            }
        }
        return null;
    }
}

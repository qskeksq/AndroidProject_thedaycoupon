package com.thedaycoupon.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 퍼미션
 */
public class PermissionLocationUtil {

    // 체크할 퍼미션
    private static String[] permissions =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static int REQ_CODE = 999;
    private volatile static PermissionLocationUtil permission;
    private LocationPermissionCallBack callBack;

    /**
     * 데이터베이스 연결이 아니고, 가장 먼저 하는 일이기 때문에 싱글턴으로 해도 메모리 누수가 생기지 않는다.
     */
    public static PermissionLocationUtil getInstance() {
        if (permission == null) {
            permission = new PermissionLocationUtil();
        }
        return permission;
    }

    public void checkVersion(Activity activity, LocationPermissionCallBack callback) {
        this.callBack = callback;
        // 버전이 마시멜로 미만인 경우는 패스
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callback.postLocationPermission();
            // 이상인 경우는 일단 허용이 된 퍼미션이 무엇인지 체크한다.
        } else {
            checkAlreadyGrantedPermission(activity);
        }
    }

    /**
     * 이미 체크된 퍼미션이 있는지 확인하고, 체크되지 않았다면 시스템에 onRequestPermission()으로 권한을 요청한다.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkAlreadyGrantedPermission(Activity activity) {
        boolean isAllGranted = true;
        for (String perm : permissions) {
            // 만약 원하는 퍼미션이 하나라도 허용이 안 되었다면 false로 전환
            if (activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
            }
        }
        // 만약 전부 허용이 되었다면 다음 액티비티로 넘어간다.
        if (isAllGranted) {
            callBack.postLocationPermission();
            // 허용되지 않는 것이 있다면 시스템에 권한신청한다.
        } else {
            activity.requestPermissions(permissions, REQ_CODE);
        }
    }


    /**
     * 시스템 권한체크가 끝난 후 호출
     */
    public void onResult(int[] grantResults, Activity activity) {
        boolean isAllGranted = true;
        for (int granted : grantResults) {
            if (granted != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
            }
        }
        // 허용되면 init()으로 원하는 함수를 실행하고
        if (isAllGranted) {
            callBack.postLocationPermission();
            // 허용되지 않는 것이 있다면 시스템에 권한신청한다.
        }
    }

    /**
     * MainActivity가 스스로를 넘겨주면, 이곳에서 MainActivity 를 대신해 메소드를 호출해주는 콜백 메서드
     */
    public interface LocationPermissionCallBack {
        void postLocationPermission();
    }
}

package com.thedaycoupon.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.LauncherService;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.logoImage.LogoImageInfo;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.DialogUtil;
import com.thedaycoupon.util.GoUtil;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.NetworkUtil;
import com.thedaycoupon.util.PreferenceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LauncherActivity extends AppCompatActivity
        implements LauncherService.ILauncherService, DialogUtil.ISimpleDialog {

    private String TAG = getClass().getSimpleName();
    private int serverVersion;
    private List<Coupon> couponList;
    private Iterator<Coupon> iterator;
    private File logoFile;
    private LauncherService launcherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        if(NetworkUtil.isConnected(this)) {
            launcherService = new LauncherService(this, this);
            launcherService.getServerVersion();
            return;
        }
        checkIsFirstEntry();
    }

    /**
     * 버전 체크
     */
    @Override
    public void checkVersion(int localVersion, int serverVersion) {
        this.serverVersion = serverVersion;
        // 버전이 같으면 넘어간다
        if (localVersion == serverVersion) {
            LogUtil.e(TAG, "로컬과 리모트 서버 버전 같음");
            checkIsFirstEntry();
        } else {
            checkNetworkStatus();
        }
    }

    /**
     * 네트워크 확인
     */
    private void checkNetworkStatus() {
        int status = NetworkUtil.checkNetworkStatus(this);
        switch (status) {
            case ConnectivityManager.TYPE_WIFI:
                updateData();
                break;
            case ConnectivityManager.TYPE_MOBILE:
                showNetworkStatusDialog();
                break;
            default:
                checkIsFirstEntry();
                break;
        }
    }

    private void showNetworkStatusDialog() {
        DialogUtil.showDialog(this,
                getResources().getString(R.string.dialog_request_network),
                getResources().getString(R.string.dialog_request_allow),
                getResources().getString(R.string.dialog_request_wifi),
                this, Const.DIALOG_SELECT_NETWORK );
    }

    /**
     * 서버 데이터로 업데이트
     */
    private void updateData(){
        LogUtil.e(TAG, "로컬과 리모트 서버 버전 다름");
        // 로컬 데이터를 모두 지우고
        CouponDao.getInstance(this).deleteAll();
        launcherService.loadCoupon();
    }

    /**
     * 쿠폰 데이터 로컬에 저장
     */
    @Override
    public void createCoupon(Coupon[] couponArray) {
        couponList = Arrays.asList(couponArray);
        setLogoFilePath();
        CouponDao.getInstance(LauncherActivity.this).create(couponList);
        LogUtil.e(TAG, "로컬에 쿠폰 저장 완료");
    }

    // 로고 파일 저장소
    private void setLogoFilePath() {
        for (Coupon coupon : couponList) {
            coupon.logoFilePath = getFileStreamPath(coupon.logoFilename).getPath();
        }
    }

    /**
     * 로고파일 체크
     */
    @Override
    public void checkLogoFileExists() {
        if (iterator == null) iterator = couponList.iterator();
        if (iterator.hasNext()) {
            String logoFileName = iterator.next().logoFilename;
            logoFile = getFileStreamPath(logoFileName);
            if (logoFile.exists()) {
                checkLogoFileExists();
                return;
            }
            callLogoService(logoFileName);
            return;
        }
        // 로고파일까지 모두 확인한 후에 버전을 저장해주자
        saveServerVersion();
        checkIsFirstEntry();
    }

    /**
     * 로고파일 받아옴
     * TODO 만약 더이상 사용하지 않으면 어떻게 하지?
     */
    private void callLogoService(String logoFileName) {
        launcherService.loadLogoFile(logoFileName);
    }

    /**
     * 로고파일 로컬에 저장
     */
    @Override
    public void createLogoFile(LogoImageInfo info) {
        try {
            OutputStream fos = new FileOutputStream(logoFile);
            fos.write(info.getLogoImage().data);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        checkLogoFileExists();
    }

    /**
     * 버전 저장
     */
    private void saveServerVersion() {
        PreferenceUtil.putInt(LauncherActivity.this, Const.PREF_KEY_LOCAL_DATA_VERSION, serverVersion);
    }

    /**
     * 처음 입장인지 확인(intro 페이지 보여줄지 결정)
     */
    public void checkIsFirstEntry() {
        // 2.1 처음 앱을 설치한 경우
        if (!PreferenceUtil.getBoolean(this, Const.PREF_KEY_IS_FIRST)) {
            LogUtil.e(TAG, "처음 입장");
            handler.sendEmptyMessageDelayed(0, 100);
            // 2.2 다음 입장부터
        } else {
            LogUtil.e(TAG, "소개페이지 넘어감");
            handler.sendEmptyMessageDelayed(1, 100);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    GoUtil.startActivity(LauncherActivity.this, IntroductionActivity.class);
                    break;
                case 1:
                    GoUtil.startActivity(LauncherActivity.this, MainActivity.class);
                    break;
            }
            finish();
        }
    };

    @Override
    public void onSimplePositiveButton(int id) {
        updateData();
    }

    @Override
    public void onSimpleNegativeButton(int id) {
        GoUtil.goSettings(this);
    }

    @Override
    public void onSimpleCanceled(int id) {
        GoUtil.goSettings(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Const.REQ_CODE_WIFI_SETTINGS){
            Log.e("확인", "확인");
            checkNetworkStatus();
        }
    }
}

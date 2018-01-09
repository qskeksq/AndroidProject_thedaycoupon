package com.thedaycoupon.activity.RemoteService;

import android.content.Context;

import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponInfo;
import com.thedaycoupon.domain.logoImage.LogoImageInfo;
import com.thedaycoupon.domain.version.VersionInfo;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017. 12. 12..
 */
public class LauncherService {

    private String TAG = getClass().getSimpleName();
    private ILauncherService service;
    private Context context;

    public LauncherService(ILauncherService service, Context context) {
        this.context = context;
        this.service = service;
    }

    /**
     * 콜백 인터페이스
     */
    public interface ILauncherService {
        void checkVersion(int localVersion, int serverVersion);

        void checkIsFirstEntry();

        void createCoupon(Coupon[] couponArray);

        void checkLogoFileExists();

        void createLogoFile(LogoImageInfo info);
    }

    /**
     * 버전 체크 GET
     */
    public void getServerVersion() {
        IRemoteService versionService = ServiceGenerator.createService(IRemoteService.class);
        Call<VersionInfo> call = versionService.selectCouponInfoVersion();
        call.enqueue(versionInfoCallback);
    }

    Callback<VersionInfo> versionInfoCallback = new Callback<VersionInfo>() {
        @Override
        public void onResponse(Call<VersionInfo> call, Response<VersionInfo> response) {
            if (response.isSuccessful()) {
                postVersionResponse(response.body());
                return;
            }
            service.checkIsFirstEntry();
            LogUtil.e(TAG, "checkVersion 서버 오류");
            NoticeUtil.makeToast(context, "다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<VersionInfo> call, Throwable t) {
            service.checkIsFirstEntry();
            LogUtil.e(TAG, "checkVersion 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postVersionResponse(VersionInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            int localVersion = PreferenceUtil.getInt(context, Const.PREF_KEY_LOCAL_DATA_VERSION);
            int serverVersion = Integer.parseInt(info.getVersion());
            service.checkVersion(localVersion, serverVersion);
        }
    }

    /**
     * 쿠폰 데이터 GET
     */
    public void loadCoupon() {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<CouponInfo> call = service.selectCouponInfoByAll();
        call.enqueue(couponInfoCallback);
    }

    Callback<CouponInfo> couponInfoCallback = new Callback<CouponInfo>() {
        @Override
        public void onResponse(Call<CouponInfo> call, Response<CouponInfo> response) {
            if (response.isSuccessful()) {
                postCouponResponse(response.body());
                return;
            }
            service.checkIsFirstEntry();
            LogUtil.e(TAG, "checkVersion 서버 오류");
            NoticeUtil.makeToast(context, "다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<CouponInfo> call, Throwable t) {
            service.checkIsFirstEntry();
            LogUtil.e(TAG, "loadCoupon 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postCouponResponse(CouponInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            service.createCoupon(info.getCoupon());
            service.checkLogoFileExists();
        }
    }

    /**
     * 로고 파일 GET
     */
    public void loadLogoFile(String filename) {
        final IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<LogoImageInfo> call = service.selectLogoByName(filename);
        call.enqueue(logoImageInfoCallback);
    }

    Callback<LogoImageInfo> logoImageInfoCallback = new Callback<LogoImageInfo>() {
        @Override
        public void onResponse(Call<LogoImageInfo> call, Response<LogoImageInfo> response) {
            if (response.isSuccessful()) {
                postLogoFileResponse(response.body());
                return;
            }
            LogUtil.e("초기 데이터 호출", "checkVersion 서버 오류");
            NoticeUtil.makeToast(context, "다시 시도해주세요");
            service.checkIsFirstEntry();
        }

        @Override
        public void onFailure(Call<LogoImageInfo> call, Throwable t) {
            LogUtil.e(TAG, "loadCoupon 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
            service.checkLogoFileExists();
        }
    };

    private void postLogoFileResponse(LogoImageInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            service.createLogoFile(info);
        }
    }


}

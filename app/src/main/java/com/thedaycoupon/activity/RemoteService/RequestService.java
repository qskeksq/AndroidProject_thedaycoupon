package com.thedaycoupon.activity.RemoteService;

import android.content.Context;

import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponInfo;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.RemoteUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017. 12. 15..
 */
public class RequestService {

    private String TAG = getClass().getSimpleName();
    private Context context;
    private Map<String, Coupon> couponMap;
    private MultipartBody.Part partLogoFile;
    private List<MultipartBody.Part> partImageFiles;

    public RequestService(Context context) {
        this.context = context;
    }

    public boolean prepare(Coupon mCoupon, File logoFile, List<File> imageFiles){
        // 쿠폰 정보
        couponMap = new HashMap<>();
        couponMap.put("coupon", mCoupon);
        // 로고파일
        partLogoFile = RemoteUtil.prepareFilePart(logoFile);
        // 이미지파일 멀티파트
        partImageFiles = new ArrayList<>();
        for (File imageFile : imageFiles) {
            partImageFiles.add(RemoteUtil.prepareFilePart(imageFile));
        }
        return true;
    }

    public void loadRequest(){
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<CouponInfo> call = service.insertRequestInfo(partLogoFile, partImageFiles, couponMap);
        call.enqueue(infoCallback);
    }

    private Callback<CouponInfo> infoCallback = new Callback<CouponInfo>() {
        @Override
        public void onResponse(Call<CouponInfo> call, Response<CouponInfo> response) {
            if(response.isSuccessful()){
                postLoadRequestResponse(response.body());
                return;
            }

        }
        @Override
        public void onFailure(Call<CouponInfo> call, Throwable t) {

        }
    };

    private void postLoadRequestResponse(CouponInfo info){
        if(info.getRESULT().getCODE().equals(Const.OK)){

        }
    }

}

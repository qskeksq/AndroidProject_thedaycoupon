package com.thedaycoupon.activity.RemoteService;

import android.content.Context;

import com.thedaycoupon.domain.detailImage.DetailImage;
import com.thedaycoupon.domain.detailImage.DetailImageInfo;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.LogUtil;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017. 12. 13..
 */
public class DetailService {

    private String TAG = getClass().getSimpleName();
    private IDetailService service;
    private Context context;

    public DetailService(IDetailService service, Context context) {
        this.context = context;
        this.service = service;
    }

    public interface IDetailService {
        void setImagePagerGone();
        void setData(List<DetailImage> imageList);
    }

    /**
     * 상세 이미지 URL POST
     */
    public void loadDetailImage(int parentNo) {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<DetailImageInfo> call = service.selectCouponDetailImageByName(parentNo);
        call.enqueue(detailImageInfoCallback);
    }

    Callback<DetailImageInfo> detailImageInfoCallback = new Callback<DetailImageInfo>() {
        @Override
        public void onResponse(Call<DetailImageInfo> call, Response<DetailImageInfo> response) {
            if (response.isSuccessful()) {
                postDetailImageResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "checkVersion 서버 오류");
        }

        @Override
        public void onFailure(Call<DetailImageInfo> call, Throwable t) {
            LogUtil.e(TAG, "checkVersion 서버 오류");
        }
    };

    private void postDetailImageResponse(DetailImageInfo info){
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            List<DetailImage> detailImages = Arrays.asList(info.getDetailImage());
            if (detailImages.size() == 0) {
                return;
            }
            service.setData(detailImages);
        }
    }

}

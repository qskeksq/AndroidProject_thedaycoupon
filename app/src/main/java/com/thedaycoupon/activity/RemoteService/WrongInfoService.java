package com.thedaycoupon.activity.RemoteService;

import android.content.Context;

import com.thedaycoupon.domain.wrongInfo.WrongInfo;
import com.thedaycoupon.domain.wrongInfo.WrongInfoInfo;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.NoticeUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017. 12. 13..
 */

public class WrongInfoService {

    private String TAG = getClass().getSimpleName();
    private Context context;

    public WrongInfoService(Context context) {
        this.context = context;
    }

    /**
     * 잘못된 정보 POST
     */
    public void createWrongInfo(WrongInfo wrongInfo) {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<WrongInfoInfo> call = service.insertWrongInfo(wrongInfo);
        call.enqueue(wrongInfoCallback);
    }

    Callback<WrongInfoInfo> wrongInfoCallback = new Callback<WrongInfoInfo>() {
        @Override
        public void onResponse(Call<WrongInfoInfo> call, Response<WrongInfoInfo> response) {
            if (response.isSuccessful()) {
                postWrongInfoResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "createWrongInfo 서버 오류");
            NoticeUtil.makeToast(context, "다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<WrongInfoInfo> call, Throwable t) {
            LogUtil.e(TAG, "createWrongInfo 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postWrongInfoResponse(WrongInfoInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            LogUtil.e(TAG, "createWrongInfo 생성 완료");
            NoticeUtil.makeToast(context, "요청이 완료되었습니다 감사합니다");
        }
    }
}

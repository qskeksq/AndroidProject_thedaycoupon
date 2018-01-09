package com.thedaycoupon.activity.RemoteService;

import android.content.Context;

import com.thedaycoupon.domain.favorite.Favorite;
import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.domain.favorite.FavoriteInfo;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.domain.member.MemberInfo;
import com.thedaycoupon.domain.tempFavorite.TempFavorite;
import com.thedaycoupon.domain.tempFavorite.TempFavoriteDao;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.PreferenceUtil;
import com.thedaycoupon.util.SignInUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017. 12. 16..
 */

public class MainService {

    private String TAG = getClass().getSimpleName();
    private Context context;
    private Favorite mFavorite;
    private int parentNo;

    public MainService(Context context) {
        this.context = context;
    }

    /**
     * 임시 아이디 생성
     */
    public void createTempMemberInfo(Member member) {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.insertMemberInfo(member);
        call.enqueue(createTempMemberCallback);
    }

    Callback<MemberInfo> createTempMemberCallback = new Callback<MemberInfo>() {
        @Override
        public void onResponse(Call<MemberInfo> call, Response<MemberInfo> response) {
            if (response.isSuccessful()) {
                postCreateTempMemberResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "createTempMemberInfo 서버오류");
            PreferenceUtil.putInt(context, Const.PREF_KEY_TEMP_ID_ON_SERVER, Const.TEMP_ID_NOT_ON_SERVER);

        }

        @Override
        public void onFailure(Call<MemberInfo> call, Throwable t) {
            LogUtil.e(TAG, "createTempMemberInfo 네트워크 오류");
            PreferenceUtil.putInt(context, Const.PREF_KEY_TEMP_ID_ON_SERVER, Const.TEMP_ID_NOT_ON_SERVER);
        }
    };

    private void postCreateTempMemberResponse(MemberInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            PreferenceUtil.putInt(context, Const.PREF_KEY_TEMP_ID_ON_SERVER, Const.TEMP_ID_ON_SERVER);
            return;
        }
        LogUtil.e(TAG, "createTempMemberInfo 서버오류");
        PreferenceUtil.putInt(context, Const.PREF_KEY_TEMP_ID_ON_SERVER, Const.TEMP_ID_NOT_ON_SERVER);
    }

    /**
     * 즐겨찾기 정보 POST
     */
    public void createFavorite(Favorite favorite) {
        // 서버 연동에 실패할 경우를 대비해 내쿠폰함에 들어갈 때마다 onServer를 확인한다
        this.mFavorite = favorite;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.insertFavoriteInfo(favorite);
        call.enqueue(createFavoriteCallback);
    }

    Callback<FavoriteInfo> createFavoriteCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                postCreateFavoriteResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "createFavoriteCallback 서버 오류");
            setOffServer();
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e(TAG, "createFavoriteCallback 네트워크 오류");
            setOffServer();
        }
    };

    private void postCreateFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            setOnServer();
            return;
        }
        LogUtil.e(TAG, "createFavoriteCallback 서버 오류");
        setOffServer();
    }

    private void setOnServer() {
        FavoriteDao.getInstance(context).updateOnOffServer(mFavorite.parentNo, Const.ON_SERVER);
    }

    private void setOffServer() {
        FavoriteDao.getInstance(context).updateOnOffServer(mFavorite.parentNo, Const.OFF_SERVER);
    }

    /**
     * 즐겨찾기 정보 DELETE
     */
    public void deleteFavorite(int parentNo) {
        this.parentNo = parentNo;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.deleteFavoriteInfo(SignInUtil.getId(context), parentNo);
        call.enqueue(deleteFavoriteCallback);
    }

    Callback<FavoriteInfo> deleteFavoriteCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                postDeleteFavoriteResponse(response.body());
                return;
            }
            createTempFavorite();
            deleteFavorite();
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            createTempFavorite();
            deleteFavorite();
        }
    };

    private void postDeleteFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            FavoriteDao.getInstance(context).delete(parentNo);
            return;
        }
        createTempFavorite();
        deleteFavorite();
    }

    private void createTempFavorite() {
        LogUtil.e(TAG, "deleteFavoriteCallback 서버 오류");
        TempFavoriteDao.getInstance(context).create(new TempFavorite(parentNo, context));
    }

    private void deleteFavorite() {
        FavoriteDao.getInstance(context).delete(parentNo);
    }

}

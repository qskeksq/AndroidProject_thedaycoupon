package com.thedaycoupon.activity.RemoteService;

import android.content.Context;

import com.thedaycoupon.domain.favorite.Favorite;
import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.domain.favorite.FavoriteInfo;
import com.thedaycoupon.domain.tempFavorite.TempFavorite;
import com.thedaycoupon.domain.tempFavorite.TempFavoriteDao;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.SignInUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017. 12. 13..
 */

public class FavoriteService {

    private String TAG = getClass().getSimpleName();
    private IFavoriteService service;
    private Context context;
    private int parentNo;

    public FavoriteService(IFavoriteService service, Context context) {
        this.context = context;
        this.service = service;
    }

    /**
     * 콜백 인터페이스
     */
    public interface IFavoriteService {
        void checkUnDeleteLeftOvers();
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
            }
            createTempFavorite();
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            createTempFavorite();
        }
    };

    private void postDeleteFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            deleteLocalFavorite();
            return;
        }
        createTempFavorite();
    }

    private void deleteLocalFavorite() {
        FavoriteDao.getInstance(context).delete(parentNo);
    }

    private void createTempFavorite() {
        LogUtil.e(TAG, "deleteFavoriteCallback 서버 오류");
        // 1. TempFavorite 테이블로 옮겨준다
        TempFavoriteDao.getInstance(context).create(new TempFavorite(parentNo, context));
        // 2. 사용자의 눈에서는 사라져야 하니까 Favorite 테이블에서 삭제
        FavoriteDao.getInstance(context).delete(parentNo);
    }

    /**
     * 미반영 즐겨찾기 POST
     */
    public void loadLeftOvers(List<Favorite> leftOvers) {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.insertFavoriteLeftOver(leftOvers);
        call.enqueue(favoriteInfoCallback);
    }

    Callback<FavoriteInfo> favoriteInfoCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                LogUtil.e(TAG, "loadFavorite 성공");
                postLoadFavoriteResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "loadFavorite 서버 오류");
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e(TAG, "loadFavorite 네트워크 오류");
        }
    };

    private void postLoadFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            FavoriteDao.getInstance(context).updateOnOffServer(1);
            service.checkUnDeleteLeftOvers();
        }
    }

    /**
     * 삭제되지 못한 즐겨찾기 삭제 삭제
     */
    public void deleteLeftOver(List<TempFavorite> tempFavorite) {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.deleteFavoriteLeftOvers(tempFavorite);
        call.enqueue(favoriteInfoCallback2);
    }

    Callback<FavoriteInfo> favoriteInfoCallback2 = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                LogUtil.e(TAG, "deleteLeftOver 성공");
                postDeleteLeftOverResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "deleteLeftOver 서버 오류");
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e("leftover", "deleteLeftOver 네트워크 오류");
        }
    };

    private void postDeleteLeftOverResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            TempFavoriteDao.getInstance(context).deleteAll();
        }
    }


}

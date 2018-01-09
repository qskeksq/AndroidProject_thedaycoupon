package com.thedaycoupon.activity.RemoteService;

import android.content.Context;

import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.domain.favorite.FavoriteInfo;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.domain.member.MemberInfo;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kakao.usermgmt.StringSet.id;

/**
 * Created by mac on 2017. 12. 16..
 */

public class SignUpService {

    private String TAG = getClass().getSimpleName();
    private ISignUpService service;
    private Context context;

    private Member mMember;

    public SignUpService(ISignUpService service, Context context) {
        this.context = context;
        this.service = service;
    }

    public interface ISignUpService {
        void setIsUniqueId(boolean uniqueId);

        void updateFavorite();

        void goMain(String id, String name, boolean hasChange);
    }

    /**
     * 아이디 중복 검사
     */
    public void checkIdExists(String id) {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.searchMemberInfo("signup:"+id);
        call.enqueue(checkIdExistsCallback);
    }

    Callback<MemberInfo> checkIdExistsCallback = new Callback<MemberInfo>() {
        @Override
        public void onResponse(Call<MemberInfo> call, Response<MemberInfo> response) {
            if (response.isSuccessful()) {
                postCheckIdResponse(response.body());
                return;
            }
            service.setIsUniqueId(false);
            LogUtil.e(TAG, "checkIdExistsCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "아이디 체크 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<MemberInfo> call, Throwable t) {
            service.setIsUniqueId(false);
            LogUtil.e(TAG, "checkIdExistsCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postCheckIdResponse(MemberInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            if (info.getRESULT().getMESSAGE().equals(Const.EXISTS)) {
                service.setIsUniqueId(false);
                NoticeUtil.makeToast(context, "이미 존재하는 아이디입니다");
                return;
            }
            service.setIsUniqueId(true);
            NoticeUtil.makeToast(context, "아이디 확인이 완료되었습니다");
            return;
        }
        service.setIsUniqueId(false);
        LogUtil.e(TAG, "checkIdExistsCallback 네트워크 오류");
        NoticeUtil.makeToast(context, "아이디 체크 오류, 다시 시도해주세요");
    }

    /**
     * 회원가입
     */
    public void signUp(Member member) {
        this.mMember = member;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.insertMemberInfo(member, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(signUpCallback);
    }

    Callback<MemberInfo> signUpCallback = new Callback<MemberInfo>() {
        @Override
        public void onResponse(Call<MemberInfo> call, Response<MemberInfo> response) {
            if (response.isSuccessful()) {
                postSignUpResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "signUpCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "회원가입 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<MemberInfo> call, Throwable t) {
            LogUtil.e(TAG, "signUpCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postSignUpResponse(MemberInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            checkTempFavoriteExists();
            return;
        }
        LogUtil.e(TAG, "signUpCallback 네트워크 오류");
        NoticeUtil.makeToast(context, "아이디 체크 오류, 다시 시도해주세요");
    }

    private void checkTempFavoriteExists() {
        // 임시아이디로 저장한 데이터가 있으면 서버에 통함
        if (FavoriteDao.getInstance(context).readAll().size() != 0) {
            service.updateFavorite();
            // 임시아이디로 저장한 데이터가 없어
        } else {
            goMain(false);
        }
    }

    /**
     * 업데이트
     */
    public void updateFavorite() {
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.updateTempFavorite(id, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(updateFavoriteCallback);
    }

    Callback<FavoriteInfo> updateFavoriteCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                postUpdateFavoriteResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "updateFavoriteCallback 서버 오류");
            goMain(false);
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e(TAG, "updateFavoriteCallback 네트워크 오류");
            goMain(false);
        }
    };

    private void postUpdateFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            // 데이터 아이디를 회원아이디로 바꿔줌
            updateFavoriteMemberId(mMember.memberId);
            // 서버에 올라갔음을 명시
            updateFavoriteOnServer();
            // Main으로 넘어감
            goMain(false);
            return;
        }
        LogUtil.e(TAG, "updateFavoriteCallback 서버 오류");
        goMain(false);
    }

    private void updateFavoriteMemberId(String id) {
        FavoriteDao.getInstance(context).updateMemberId(id);
    }

    private void updateFavoriteOnServer() {
        FavoriteDao.getInstance(context).updateOnOffServer(Const.ON_SERVER);
    }

    private void goMain(boolean hasChange) {
        service.goMain(mMember.memberId, mMember.username, hasChange);
    }


}

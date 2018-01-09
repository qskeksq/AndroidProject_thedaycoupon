package com.thedaycoupon.activity.RemoteService;

import android.content.Context;
import android.util.Log;

import com.thedaycoupon.R;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.favorite.Favorite;
import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.domain.favorite.FavoriteInfo;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.domain.member.MemberInfo;
import com.thedaycoupon.remote.IRemoteService;
import com.thedaycoupon.remote.ServiceGenerator;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.DialogUtil;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PreferenceUtil;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017. 12. 15..
 */

public class SignInService implements DialogUtil.ISimpleDialog {

    private String TAG = getClass().getSimpleName();
    private ISignInService service;
    private Context context;

    private String sortedId, name;
    private boolean isFirst;

    public SignInService(ISignInService service, Context context) {
        this.context = context;
        this.service = service;
    }

    /**
     * 콜백
     */
    public interface ISignInService {
        void deleteTempFavorite(boolean isFirst, String id, String name);

        void deleteSignedUpFavorite(String id, String name);

        void selectFavorite(String id, String name);

        void insertMember(String id, String name);

        void updateFavorite(String id, String name);

        void goMain(String id, String name, boolean hasChange);
    }

    /**
     * 회원가입으로 로그인
     */
    public void login(Member member) {
        this.sortedId = member.memberId;
        this.name = member.username;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.login(member);
        call.enqueue(logInCallback);
    }

    Callback<MemberInfo> logInCallback = new Callback<MemberInfo>() {
        @Override
        public void onResponse(Call<MemberInfo> call, Response<MemberInfo> response) {
            if (response.isSuccessful()) {
                postLoginResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "logInCallback 서버 오류");
            NoticeUtil.makeToast(context, "로그인 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<MemberInfo> call, Throwable t) {
            LogUtil.e(TAG, "logInCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postLoginResponse(MemberInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            checkMemberBySignUpExists(info.getRESULT().getMESSAGE().equals(Const.EXISTS), info);
            return;
        }
        LogUtil.e(TAG, "logInCallback 서버 오류");
        NoticeUtil.makeToast(context, "로그인 오류, 다시 시도해주세요");
    }

    private void checkMemberBySignUpExists(boolean exists, MemberInfo info) {
        // 회원가입을 한 경우
        if (exists) {
            setUsername(info.getMember()[0].username);
            checkTempFavoriteExists();
            return;
        }
        // 회원가입을 안 했거나 비밀번호가 틀린 경우
        NoticeUtil.makeToast(context, "아이디 혹은 비밀번호를 확인해주세요");
    }

    private void setUsername(String name) {
        this.name = name;
    }


    /**
     * 회원가입 여부
     */
    public void searchMember(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.searchMemberInfo(id);
        call.enqueue(searchMemberInfoCallback);
    }

    Callback<MemberInfo> searchMemberInfoCallback = new Callback<MemberInfo>() {
        @Override
        public void onResponse(Call<MemberInfo> call, Response<MemberInfo> response) {
            if (response.isSuccessful()) {
                postSearchMemberResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "searchMemberInfoCallback 서버 오류");
            NoticeUtil.makeToast(context, "멤버이력 검색 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<MemberInfo> call, Throwable t) {
            LogUtil.e(TAG, "searchMemberInfoCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postSearchMemberResponse(MemberInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            checkMemberExists(info.getRESULT().getMESSAGE().equals(Const.EXISTS));
            return;
        }
        LogUtil.e(TAG, "searchMemberInfoCallback 서버 오류");
        NoticeUtil.makeToast(context, "멤버이력 검색 오류, 다시 시도해주세요");

    }

    private void checkMemberExists(boolean exists) {
        if (exists) {
            checkTempFavoriteExists();
            return;
        }
        // 회원가입 이력이 없는 경우 회원 정보 서버 입력
        service.insertMember(sortedId, name);
    }

    private void checkTempFavoriteExists() {
        // 임시아이디로 저장한 데이터가 존재
        if (FavoriteDao.getInstance(context).readAll().size() != 0) {
            showPostSearchMemberDialog();
            return;
        }
        // 임시아이디로 저장한 데이터가 없는 경우 서버에서 받아오기만 한다
        service.selectFavorite(sortedId, name);
    }

    private void showPostSearchMemberDialog() {
        DialogUtil.showDialog(context,
                context.getResources().getString(R.string.sign_in_integrate),
                context.getResources().getString(R.string.sign_in_yes),
                context.getResources().getString(R.string.sign_in_no),
                this, Const.DIALOG_SEARCH_MEMBER);
    }

    /**
     * 신규멤버 생성
     */
    public void insertMember(Member member, String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.insertMemberInfo(member, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(insertMemberInfoCallback);
    }

    Callback<MemberInfo> insertMemberInfoCallback = new Callback<MemberInfo>() {
        @Override
        public void onResponse(Call<MemberInfo> call, Response<MemberInfo> response) {
            if (response.isSuccessful()) {
                postInsertMemberResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "insertMemberInfoCallback 서버 오류");
            NoticeUtil.makeToast(context, "신규멤버 생성 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<MemberInfo> call, Throwable t) {
            LogUtil.e(TAG, "insertMemberInfoCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "신규멤버 생성 오류, 다시 시도해주세요");
            LogUtil.e(TAG, t.toString());
        }
    };

    private void postInsertMemberResponse(MemberInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            // 임시아이디로 저장한 데이터가 있으면 서버에 통함
            if (FavoriteDao.getInstance(context).readAll().size() != 0) {
                showPostInsertMemberDialog();
                return;
            }
            // 임시아이디로 저장한 데이터가 없으면 Main 으로 넘어감
            goMain(false);
            return;
        }
        LogUtil.e(TAG, "insertMemberInfoCallback 서버 오류");
        NoticeUtil.makeToast(context, "신규멤버 생성 오류, 다시 시도해주세요");
    }

    private void showPostInsertMemberDialog() {
        DialogUtil.showDialog(context,
                context.getResources().getString(R.string.sign_in_integrate_at_first),
                context.getResources().getString(R.string.sign_in_yes),
                context.getResources().getString(R.string.sign_in_no),
                this, Const.DIALOG_INSERT_MEMBER);
    }

    /**
     * 서버에 저장된 즐겨찾기 가져오기
     */
    public void selectFavorite(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.selectFavoriteInfo(id);
        call.enqueue(selectFavoriteInfoCallback);
    }

    Callback<FavoriteInfo> selectFavoriteInfoCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                postSelectFavoriteResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "selectFavoriteInfoCallback 서버 오류");
            NoticeUtil.makeToast(context, "즐겨찾기 받아오기 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e(TAG, "selectFavoriteInfoCallback 서버 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postSelectFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            List<Favorite> favoriteList = Arrays.asList(info.getFavorite());
            // 메인 화면에서 체크된 거 모두 풀어줌
            unCheckMainFavorite();
            // 임시아이디 데이터 삭제
            deleteTempFavorite();
            // 서버에서 받아온 데이터 저장
            createFavorite(favoriteList);
            // 메인화면에서 체크되도록 업데이트 TODO 업데이트 이렇게 해도 되는지 확인하자
            checkMainFavorite(favoriteList);
            // Main으로 넘어감
            goMain(true);
            return;
        }
        LogUtil.e(TAG, "selectFavoriteInfoCallback 서버 오류");
        NoticeUtil.makeToast(context, "즐겨찾기 받아오기 오류, 다시 시도해주세요");
    }

    private void unCheckMainFavorite() {
        CouponDao.getInstance(context).updateUnChecked();
    }

    private void deleteTempFavorite() {
        FavoriteDao.getInstance(context).deleteAll();
    }

    private void createFavorite(List<Favorite> favoriteList) {
        // 서버에서 받아왔기 때문에 onSever 바꿔줌
        for (Favorite favorite : favoriteList) {
            favorite.onServer = Const.ON_SERVER;
        }
        FavoriteDao.getInstance(context).create(favoriteList);
    }

    private void checkMainFavorite(List<Favorite> favoriteList) {
        for (int i = 0; i < favoriteList.size(); i++) {
            CouponDao.getInstance(context).updateChecked(favoriteList.get(i).parentNo, true);
        }
    }

    /**
     * 서버에 임시아이디로 저장한 데이터를 모두 회원가입한 아이디로 업데이트 해준다
     */
    public void updateFavorite(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.updateTempFavorite(id, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(updateFavoriteInfoCallback);
    }

    Callback<FavoriteInfo> updateFavoriteInfoCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                postUpdateFavoriteResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "updateFavoriteInfoCallback 서버 오류");
            NoticeUtil.makeToast(context, "서버 즐겨찾기 업데이트 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e(TAG, "updateFavoriteInfoCallback 서버 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postUpdateFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            // 데이터 아이디를 회원아이디로 바꿔줌
            updateFavoriteMemberId(sortedId);
            // 서버에 올라갔음을 명시
            updateFavoriteOnServer();
            // Main 으로 넘어감
            goMain(false);
            return;
        }
        LogUtil.e(TAG, "updateFavoriteInfoCallback 서버 오류");
        NoticeUtil.makeToast(context, "서버 즐겨찾기 업데이트 오류, 다시 시도해주세요");
    }

    private void updateFavoriteMemberId(String id) {
        FavoriteDao.getInstance(context).updateMemberId(id);
    }

    private void updateFavoriteOnServer() {
        FavoriteDao.getInstance(context).updateOnOffServer(Const.ON_SERVER);
    }

    /**
     * 회원아이디로 저장한 즐겨찾기 데이터 삭제 -> 임시아아디로 저장한 쿠폰 회원아이디로 바꿔줌
     */
    public void deleteSignedUpFavorite(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Log.e("데이터", id +":"+name+":"+PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        Call<FavoriteInfo> call = service.deleteSignedUpFavorite(id, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(deleteSignedUpFavoriteCallback);
    }

    Callback<FavoriteInfo> deleteSignedUpFavoriteCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                postDeleteSignedUpFavoriteResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "deleteSignedUpFavoriteCallback 서버 오류");
            NoticeUtil.makeToast(context, "회원아이디 즐겨찾기 삭제 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e(TAG, "deleteSignedUpFavoriteCallback 네트워크 오류");
            NoticeUtil.makeToast(context, "인터넷 연결을 확인해주세요");
        }
    };

    private void postDeleteSignedUpFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            // 로그인한 아이디로 바꿔주고
            updateFavoriteMemberId(sortedId);
            // 서버에 올라갔음을 명시
            updateFavoriteOnServer();
            // 서버에서 다시 불러옴
            goMain(false);
            return;
        }
        LogUtil.e(TAG, "deleteSignedUpFavoriteCallback 서버 오류");
        NoticeUtil.makeToast(context, "회원아이디 즐겨찾기 삭제 오류, 다시 시도해주세요");
    }

    private void goMain(boolean hasChange) {
        service.goMain(sortedId, name, hasChange);
    }

    /**
     * 임시아이디로 저장한 즐겨찾기 데이터 삭제
     */
    public void deleteTempFavorite(boolean isFirst, String id, String name) {
        this.isFirst = isFirst;
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.deleteTempFavorite(PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(deleteTempFavoriteCallback);
    }

    Callback<FavoriteInfo> deleteTempFavoriteCallback = new Callback<FavoriteInfo>() {
        @Override
        public void onResponse(Call<FavoriteInfo> call, Response<FavoriteInfo> response) {
            if (response.isSuccessful()) {
                postDeleteTempFavoriteResponse(response.body());
                return;
            }
            LogUtil.e(TAG, "deleteTempFavoriteCallback 서버 오류");
            NoticeUtil.makeToast(context, "임시아이디 즐겨찾기 삭제 오류, 다시 시도해주세요");
        }

        @Override
        public void onFailure(Call<FavoriteInfo> call, Throwable t) {
            LogUtil.e(TAG, "deleteTempFavoriteCallback 서버 오류");
            NoticeUtil.makeToast(context, "임시아이디 즐겨찾기 삭제 오류, 다시 시도해주세요");
        }
    };

    private void postDeleteTempFavoriteResponse(FavoriteInfo info) {
        if (info.getRESULT().getCODE().equals(Const.OK)) {
            checkIsFirst(isFirst);
            return;
        }
        LogUtil.e(TAG, "deleteTempFavoriteCallback 서버 오류");
        NoticeUtil.makeToast(context, "임시아이디 즐겨찾기 삭제 오류, 다시 시도해주세요");
    }

    private void checkIsFirst(boolean isFirst) {
        // DIALOG_INSERT_MEMBER 인 경우에 임시데이터를 삭제할 경우
        if (isFirst) {
            // 메인 화면에서 체크된 거 모두 풀어줌
            unCheckMainFavorite();
            // 임시아이디로 저장한 데이터 모두 지우고
            deleteTempFavorite();
            // main 으로 넘어감
            goMain(true);
            // DIALOG_SEARCH_MEMBER 에서 기존 회원인 경우
        } else {
            // 서버에서 불러옴
            selectFavorite(sortedId, name);
        }
    }

    @Override
    public void onSimplePositiveButton(int id) {
        switch (id) {
            case Const.DIALOG_SEARCH_MEMBER:
                service.deleteTempFavorite(false, sortedId, name);
                break;
            case Const.DIALOG_INSERT_MEMBER:
                service.updateFavorite(name, sortedId);
                break;
        }
    }

    @Override
    public void onSimpleNegativeButton(int id) {
        switch (id) {
            case Const.DIALOG_SEARCH_MEMBER:
                service.deleteSignedUpFavorite(sortedId, name);
                break;
            case Const.DIALOG_INSERT_MEMBER:
                service.deleteTempFavorite(true, sortedId, name);
                break;
        }
    }

    @Override
    public void onSimpleCanceled(int id) {
        switch (id) {
            case Const.DIALOG_SEARCH_MEMBER:
                service.deleteSignedUpFavorite(sortedId, name);
                break;
            case Const.DIALOG_INSERT_MEMBER:
                service.deleteTempFavorite(true, sortedId, name);
                break;
        }
    }
}

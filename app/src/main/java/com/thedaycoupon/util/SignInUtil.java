package com.thedaycoupon.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.thedaycoupon.activity.SignInActivity;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.domain.tempFavorite.TempFavoriteDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-12-07.
 */
public class SignInUtil {

    private static String TAG = "SignInUtil";
    private static KakaoSessionCallback kakaoCallback;
    private static CallbackManager facebookCallbackManager;
    private static GoogleApiClient googleApiClient;
    private static FacebookSessionCallback facebookCallback;

    public interface ISignInCallback {
        void searchMember(int type, String id, String name);
    }

    public static boolean hasSignedIn(String str) {
        if (str != null && !str.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean hasId(String id, String tempId) {
        if (!isEmpty(id) || !isEmpty(tempId)) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        if (str != null && !str.equals("")) {
            return false;
        }
        return true;
    }

    public static String getSortedId(int type, String id) {
        switch (type) {
            case Const.FACEBOOK:
                id = "facebook:" + id;
                break;
            case Const.GOOGLE:
                id = "google:" + id;
                break;
            case Const.KAKAO:
                id = "kakao:" + id;
                break;
        }
        return id;
    }

    public static String getId(Context context){
        String id = PreferenceUtil.getString(context, Const.PREF_KEY_ID);
        String tempId = PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID);
        if(hasSignedIn(id)) return id;
        return tempId;
    }

    public static void deleteLocalInfo(Context context) {
        // 2. 아이디 없애기
        PreferenceUtil.delete(context, Const.PREF_KEY_ID);
        // 3. 즐겨찾기 삭제
        FavoriteDao.getInstance(context).deleteAll();
        // 4. TempFavorite, Temp 파일 전부 삭제
        TempFavoriteDao.getInstance(context).deleteAll();
        // 5. 전체 데이터 즐겨찾기 unCheck
        CouponDao.getInstance(context).updateUnChecked();
    }


//=============================================================================
//    카카오톡 로그인
//=============================================================================

    public static void initKakao(Activity activity, ISignInCallback signInCallback) {
        /** 카카오 로그인 */
        kakaoCallback = new KakaoSessionCallback(activity, signInCallback);
        Session.getCurrentSession().addCallback(kakaoCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    /**
     * 카카오 로그아웃
     */
    public static void signOutKakao(final Context context, final Handler signOutHandler) {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                deleteLocalInfo(context);
                // 5. 화면 프로필 변경
                signOutHandler.sendEmptyMessage(Const.KAKAO);
            }
        });
    }

    /**
     * 카카오톡 로그인 콜백
     */
    private static class KakaoSessionCallback implements ISessionCallback {

        Activity activity;
        ISignInCallback signInCallback;

        public KakaoSessionCallback(Activity context, ISignInCallback signInCallback) {
            this.activity = context;
            this.signInCallback = signInCallback;
        }

        @Override
        public void onSessionOpened() {
            // 현재 모바일에 로그인 된 정보가 있는지 확인함
            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    NoticeUtil.makeToast(activity, "카카오톡 사용자 정보가 없습니다");
                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        activity.finish();
                    } else {
                        GoUtil.startActivity(activity, SignInActivity.class);
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    LogUtil.e(TAG, "kakao onSessionClosed");
                }

                @Override
                public void onNotSignedUp() {
                    LogUtil.e(TAG, "kakao onNotSignedUp");
                    NoticeUtil.makeToast(activity, "카카오톡 사용자 정보가 없습니다");
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    LogUtil.e(TAG, "kakao onSuccess");
//                    String token = Session.getCurrentSession().getAccessToken();
                    signInCallback.searchMember(Const.KAKAO, userProfile.getId() + "", userProfile.getNickname());
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            LogUtil.e(TAG, "kakao onSessionOpenFailed");
            Crashlytics.logException(exception);
            NoticeUtil.makeToast(activity, "카카오톡 연결오류가 발생했습니다");
        }
    }

    public static void removeKakaoCallback() {
        Session.getCurrentSession().removeCallback(kakaoCallback);
    }

//=============================================================================
//    구글 로그인
//=============================================================================

    public static void initGoogle(final FragmentActivity activity) {
        /** 구글 로그인 */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        NoticeUtil.makeToast(activity, "구글 로그인 오류");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * 구글 로그인
     */
    public static void googleSignIn(FragmentActivity activity) {
        if (googleApiClient == null) {
            initGoogle(activity);
        }
        Intent signin = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        activity.startActivityForResult(signin, Const.GOOGLE_SIGN_IN);
    }

    /**
     * 구글 로그아웃
     */
    public static void signOutGoogle(GoogleApiClient mGoogleApiClient, Context context, Handler signOutHandler) {
        if (mGoogleApiClient.isConnected()) {
            // 1. 로그아웃
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            // 2.
            deleteLocalInfo(context);
            // 3. 화면 프로필 변경
            signOutHandler.sendEmptyMessage(Const.GOOGLE);
        }
    }

    public static void disconnectGoogleApi() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    /**
     * 콜백의 결과를 onActivityResult 에서 처리해준다
     */
    public static void handleSignIn(GoogleSignInResult result, ISignInCallback signInCallback) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            LogUtil.d("토큰", account.getIdToken()+"");
            signInCallback.searchMember(Const.GOOGLE, account.getId(), account.getDisplayName());
        } else {
            Log.e(TAG, "Google 실패 : "+result.toString());
        }
    }

//=============================================================================
//    페이스북 로그인
//=============================================================================

    /**
     * Facebook login 초기화
     * @param activity
     */
    public static void initFacebook(Activity activity, ISignInCallback signInCallback) {
        facebookCallback = new FacebookSessionCallback(activity, signInCallback);
        facebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(facebookCallbackManager, facebookCallback);
    }

    /**
     * 페이스북 로그인
     */
    public static void signInFacebook(Activity activity, ISignInCallback signInCallback) {
        if (facebookCallback == null) {
            initFacebook(activity, signInCallback);
        }
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
    }

    /**
     *
     */
    public static void handleFacebookSignIn(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 페이스북 로그아웃
     */
    public static void signOutFacebook(Context context, Handler signOutHandler) {
        LoginManager.getInstance().logOut();
        deleteLocalInfo(context);
        // 5. 화면 프로필 변경
        signOutHandler.sendEmptyMessage(Const.FACEBOOK);
    }

    /**
     * 페이스북 콜백
     */
    public static class FacebookSessionCallback implements FacebookCallback<LoginResult> {

        Activity activity;
        ISignInCallback signInCallback;

        public FacebookSessionCallback(Activity activity, ISignInCallback signInCallback) {
            this.activity = activity;
            this.signInCallback = signInCallback;
        }

        @Override
        public void onSuccess(LoginResult loginResult) {

            Log.e("토큰", "토큰1:" + loginResult.getAccessToken().getToken());
            Log.e("토큰", "토큰2:" + AccessToken.getCurrentAccessToken().getToken());
            Log.e("토큰", "id:" + loginResult.getAccessToken().getUserId());


            GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        signInCallback.searchMember(Const.FACEBOOK, object.getString("id"), object.getString("name"));
                    } catch (JSONException e) {
                        Crashlytics.logException(e);
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
            AccessToken.getCurrentAccessToken();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException exception) {
            Log.e(TAG, "facebook onError:"+exception.toString());
            NoticeUtil.makeToast(activity, "페이스북 연결오류가 발생했습니다");
            Crashlytics.logException(exception);
        }
    }
}

package com.thedaycoupon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.kakao.auth.Session;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.SignInService;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.GoUtil;
import com.thedaycoupon.util.KakaoUtil;
import com.thedaycoupon.util.NetworkUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PreferenceUtil;
import com.thedaycoupon.util.SignInUtil;

public class SignInActivity extends BaseActivity
        implements View.OnClickListener, SignInUtil.ISignInCallback, SignInService.ISignInService {

    private ImageView signinBack;
    private TextView signinInfo;
    private TextView signinSignup;
    private TextView signinFindPwd;
    private EditText signinEmail;
    private EditText signinPassword;
    private Button signinBtn;
    private Button signinFacebook;
    private Button signinKakao;
    private Button signinGoogle;
    private SignInService signInService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SignInUtil.removeKakaoCallback();
        SignInUtil.disconnectGoogleApi();
    }

    private void init() {
        initLogin();
        initView();
        setListener();
        signInService = new SignInService(this, this);
    }

    private void initLogin() {
        /** 카카오 로그인 */
        SignInUtil.initKakao(this, this);

        /** 페이스북 로그인 */
        SignInUtil.initFacebook(this, this);

        /** 구글 로그인 */
        SignInUtil.initGoogle(this);
    }

    private void initView() {
        signinBack = findViewById(R.id.signinBack);
        signinInfo = findViewById(R.id.signinInfo);
        signinEmail = findViewById(R.id.signinEmail);
        signinPassword = findViewById(R.id.signinPassword);
        signinBtn = findViewById(R.id.signinBtn);
        signinFacebook = findViewById(R.id.signinFacebook);
        signinKakao = findViewById(R.id.signinKakao);
        signinGoogle = findViewById(R.id.signinGoogle);
        signinSignup = findViewById(R.id.signinSignup);
        signinFindPwd = findViewById(R.id.signinFindPwd);
    }

    private void setListener() {
        signinBack.setOnClickListener(this);
        signinSignup.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.signinBack: onBackPressed(); break;
            case R.id.signinSignup:
                GoUtil.startActivityForResult(SignInActivity.this, SignUpActivity.class, Const.REQ_CODE_LOGIN);
                break;
        }
    }

//============================================================================
//    공통사항 onActivityResult
//============================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 회원가입
        if (requestCode == Const.REQ_CODE_LOGIN) {
            if(resultCode == RESULT_OK) {
                String name = data.getStringExtra(Const.GOUTIL_EXTRA_2);
                Intent intent = new Intent();
                intent.putExtra(Const.GOUTIL_EXTRA_1, false);
                intent.putExtra(Const.GOUTIL_EXTRA_2, name);
                setResult(RESULT_OK, intent);
                finish();
            }
            return;
        }

        // 구글
        if (requestCode == Const.GOOGLE_SIGN_IN) {
            NoticeUtil.makeToast(this, "구글아이디로 로그인");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            SignInUtil.handleSignIn(result, this);
            return;
        }

        // 카카오톡
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            NoticeUtil.makeToast(this, "카카오톡으로 로그인");
            return;
        }

        // 페이스북
        NoticeUtil.makeToast(this, "페이스북으로 로그인");
        SignInUtil.handleFacebookSignIn(requestCode, resultCode, data);
    }

    /**
     * 회원가입 이력이 있는지 확인
     * 1. 이미 회원가입을 한 경우
     *  1.1 임시데이터가 있는 경우
     *    1.1.1 업데이트 요청
     *      1.1.1.1. 전부 지우고 서버 데이터 받아옴 -> 메인으로 넘어감
     *    1.1.2 업데이트 거부
     *      1.1.2.1 서버 데이터 전부 지우고 -> 이름만 바꿔줌 -> 메인으로 넘어감
     *  1.2 임시데이터가 없는 경우
     *
     * 2. 처음 회원가입한 회원인 경우
     *  2.1 임시데이터가 있는 경우
     *    2.1.1 업데이트 해주세요
     *      2.1.1.1 업데이트 -> 메인으로 넘어감
     *    2.1.2 업데이트 필요 없음 -> 메인으로 넘어감
     *  2.2 임시데이터가 없는 경우 -> 메인으로 넘어감
     */
    @Override
    public void searchMember(int type, String id, String name) {
        if(!NetworkUtil.isConnected(this)) return;
        signInService.searchMember(SignInUtil.getSortedId(type, id), name);
    }

    @Override
    public void insertMember(String id, String name) {
        signInService.insertMember(makeMember(id, name), id, name);
    }

    @Override
    public void selectFavorite(String id, String name) {
        signInService.selectFavorite(id, name);
    }

    @Override
    public void updateFavorite(String id, String name) {
        signInService.updateFavorite(id, name);
    }

    @Override
    public void deleteSignedUpFavorite(String id, String name) {
        signInService.deleteSignedUpFavorite(id, name);
    }

    @Override
    public void deleteTempFavorite(boolean isFirst, String id, String name) {
        signInService.deleteTempFavorite(isFirst, id, name);
    }

    @Override
    public void goMain(String memberId, String name, boolean hasChange) {
        PreferenceUtil.putString(this, Const.PREF_KEY_ID, memberId);
        PreferenceUtil.putString(this, Const.PREF_KEY_NAME, name);
        Intent intent = new Intent();
        intent.putExtra(Const.GOUTIL_EXTRA_1, hasChange);
        intent.putExtra(Const.GOUTIL_EXTRA_2, name);
        setResult(RESULT_OK, intent);
        finish();
    }

    private Member makeMember(String id, String name) {
        Member member = new Member();
        member.memberId = id;
        member.username = name;
        return member;
    }

//=============================================================================
//    일반 로그인
//=============================================================================

    private boolean checkIsValid(String id, String pwd) {
        if(SignInUtil.isEmpty(id)){
            NoticeUtil.makeToast(this, "아이디를 입력해주세요");
            return false;
        }
        if(SignInUtil.isEmpty(pwd)){
            NoticeUtil.makeToast(this, "비밀번호를 입력해주세요");
            return false;
        }
        return true;
    }

    private Member makeSigninMember(String id, String pwd) {
        Member member = new Member();
        member.memberId = "signup:"+id;
        member.password = pwd;
        return member;
    }

    /**
     * 일반 로그인
     */
    public void login(View view) {
        String id = signinEmail.getText().toString();
        String password = signinPassword.getText().toString();
        if(!checkIsValid(id, password)) return;
        if(!NetworkUtil.isConnected(this)) return;
        signInService.login(makeSigninMember(id, password));
    }

//=============================================================================
//    카카오톡 로그인
//=============================================================================

    public void loginKakao(View view) {
        if(!NetworkUtil.isConnected(this)) return;
        KakaoUtil.onClickSignup(this);
    }

//=============================================================================
//    페이스북 로그인
//=============================================================================

    public void loginFacebook(View view) {
        if(!NetworkUtil.isConnected(this)) return;
        SignInUtil.signInFacebook(this, this);
     }

//=============================================================================
//    구글 로그인
//=============================================================================

    public void loginGoogle(View view) {
        if(!NetworkUtil.isConnected(this)) return;
        SignInUtil.googleSignIn(this);
    }

}

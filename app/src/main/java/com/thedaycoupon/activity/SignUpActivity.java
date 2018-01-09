package com.thedaycoupon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.SignUpService;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.NetworkUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PreferenceUtil;
import com.thedaycoupon.util.SignInUtil;
import com.thedaycoupon.util.StringUtil;

public class SignUpActivity extends BaseActivity implements View.OnClickListener, SignUpService.ISignUpService {

    private ImageView signupBack;
    private TextView signupInfo;
    private TextView signupId;
    private EditText signupIdDetail;
    private TextView signupPassword;
    private EditText signupPasswordDetail;
    private TextView signupCheckPwd;
    private EditText editSignupCheckPwdDetail;
    private EditText checkIdExists;
    private EditText signupNicknameDetail;
    private Button signupSubmit;
    private boolean isValidPassword, isValidSecondPassword, isValidName, isUniqueId;
    private SignUpService signUpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        initView();
        setListener();
        signUpService = new SignUpService(this, this);
    }

    private void initView() {
        signupBack = findViewById(R.id.signupBack);
        signupInfo = findViewById(R.id.signupInfo);
        signupId = findViewById(R.id.signupId);
        signupIdDetail = findViewById(R.id.signupIdDetail);
        signupPassword = findViewById(R.id.signupPassword);
        signupPasswordDetail = findViewById(R.id.signupPasswordDetail);
        signupCheckPwd = findViewById(R.id.signupCheckPwd);
        editSignupCheckPwdDetail = findViewById(R.id.editSignupCheckPwdDetail);
        signupSubmit = findViewById(R.id.signupSubmit);
        checkIdExists = findViewById(R.id.checkIdExists);
        signupNicknameDetail = findViewById(R.id.signUpNicknameDetail);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupBack:
                onBackPressed();
                break;
            case R.id.signupSubmit:
                signup();
                break;
            case R.id.checkIdExists:
                if(!NetworkUtil.isConnected(this)) return;
                signUpService.checkIdExists(getId());
        }
    }
    private void setListener() {
        // 이름
        signupIdDetail.addTextChangedListener(signUpIdWatcher);

        // 1차 비밀번호
        signupPasswordDetail.addTextChangedListener(signUpPwdWatcher);

        // 2차 비밀번호
        editSignupCheckPwdDetail.addTextChangedListener(signUpPwd2Watcher);

        signupBack.setOnClickListener(this);

        checkIdExists.setOnClickListener(this);

        signupSubmit.setOnClickListener(this);

    }
    TextWatcher signUpIdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isValidName = StringUtil.isValidName(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!isValidName) {
                signupIdDetail.setTextColor(getResources().getColor(R.color.color_text_wrong_input));
            } else {
                signupIdDetail.setTextColor(getResources().getColor(R.color.color_text_signup));
            }
        }
    };
    TextWatcher signUpPwdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isValidPassword = StringUtil.isValidPassword(s.toString());
            isValidSecondPassword = StringUtil.isValidSecondPassword(editSignupCheckPwdDetail.getText().toString(), s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!isValidPassword) {
                signupPasswordDetail.setTextColor(getResources().getColor(R.color.color_text_wrong_input));
            } else {
                signupPasswordDetail.setTextColor(getResources().getColor(R.color.color_text_signup));
            }

            if (!isValidSecondPassword) {
                editSignupCheckPwdDetail.setTextColor(getResources().getColor(R.color.color_text_wrong_input));
            } else {
                editSignupCheckPwdDetail.setTextColor(getResources().getColor(R.color.color_text_signup));
            }
        }
    };
    TextWatcher signUpPwd2Watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isValidSecondPassword = StringUtil.isValidSecondPassword(signupPasswordDetail.getText().toString(), s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!isValidSecondPassword) {
                editSignupCheckPwdDetail.setTextColor(getResources().getColor(R.color.color_text_wrong_input));
            } else {
                editSignupCheckPwdDetail.setTextColor(getResources().getColor(R.color.color_text_signup));
            }
        }
    };

    private String getId() {
        return signupIdDetail.getText().toString();
    }

    public void signup() {
        // 유효성 검사
        if(!checkIsValid()) return;
        // 네트워크 검사
        if(!NetworkUtil.isConnected(this)) return;
        // 멤버
        Member member = makeMember();
        signUpService.signUp(member);
    }

    private boolean checkIsValid(){
        if (!isUniqueId) {
            Toast.makeText(this, "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidName) {
            Toast.makeText(this, "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword) {
            Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidSecondPassword) {
            Toast.makeText(this, "2차 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(SignInUtil.isEmpty(signupNicknameDetail.getText().toString())){
            Toast.makeText(this, "닉네임을 확인해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Member makeMember(){
        Member member = new Member();
        member.memberId = "signup:" + signupIdDetail.getText().toString();
        member.password = signupPasswordDetail.getText().toString();
        member.username = signupNicknameDetail.getText().toString();
        return member;
    }

    /**
     * 서버에 임시아이디로 저장한 데이터를 모두 회원가입한 아이디로 업데이트 해준다
     */
    @Override
    public void updateFavorite() {
        signUpService.updateFavorite();
    }

    @Override
    public void goMain(String memberId, String name, boolean hasChange) {
        PreferenceUtil.putString(this, Const.PREF_KEY_ID, memberId);
        PreferenceUtil.putString(this, Const.PREF_KEY_NAME, name);
        Intent intent = new Intent();
        intent.putExtra(Const.GOUTIL_EXTRA_1, hasChange);
        intent.putExtra(Const.GOUTIL_EXTRA_2, name);
        setResult(RESULT_OK, intent);
        NoticeUtil.makeToast(this, "회원가입이 완료되었습니다");
        finish();
    }

    @Override
    public void setIsUniqueId(boolean uniqueId) {
        this.isUniqueId = uniqueId;
    }
}

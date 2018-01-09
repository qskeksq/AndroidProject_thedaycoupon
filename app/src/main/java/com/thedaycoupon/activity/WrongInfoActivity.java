package com.thedaycoupon.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.WrongInfoService;
import com.thedaycoupon.domain.wrongInfo.WrongInfo;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.NetworkUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.StringUtil;

public class WrongInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView wrongInfoCouponName,wrongInfoContent,wrongInfoCouponEmail,wrongInfoTel;
    private EditText wrongInfoCouponNameDetail,wrongInfoContentDetail,wrongInfoCouponEmailDetail,
                     wrongInfoTelDetail;
    private ImageView wrongInfoBack;
    private Button wrongInfoSubmit;
    private String title;
    private int parentNo;
    private WrongInfoService wrongInfoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_info);
        init();
    }

    private void init(){
        getIntentExtra();
        initView();
        setFont();
        setListener();
        wrongInfoService = new WrongInfoService(this);
    }

    private void getIntentExtra() {
        title = getIntent().getStringExtra(Const.GOUTIL_EXTRA_1);
        parentNo = getIntent().getIntExtra(Const.GOUTIL_EXTRA_2, 0);
    }

    private void initView() {
        wrongInfoCouponName = findViewById(R.id.wrongInfoCouponName);
        wrongInfoContent = findViewById(R.id.wrongInfoContent);
        wrongInfoCouponEmail = findViewById(R.id.wrongInfoCouponEmail);
        wrongInfoTel = findViewById(R.id.wrongInfoTel);
        wrongInfoCouponNameDetail = findViewById(R.id.wrongInfoCouponNameDetail);
        wrongInfoContentDetail = findViewById(R.id.wrongInfoContentDetail);
        wrongInfoCouponEmailDetail = findViewById(R.id.wrongInfoCouponEmailDetail);
        wrongInfoTelDetail = findViewById(R.id.wrongInfoTelDetail);
        wrongInfoBack = findViewById(R.id.wrongInfoBack);
        wrongInfoSubmit = findViewById(R.id.wrongInfoSubmit);
        wrongInfoCouponNameDetail.setText(title);
    }

    private void setFont() {
        StringUtil.setTypefaceNanumB(this, wrongInfoCouponName);
        StringUtil.setTypefaceNanumB(this, wrongInfoContent);
        StringUtil.setTypefaceNanumB(this, wrongInfoCouponEmail);
        StringUtil.setTypefaceNanumB(this, wrongInfoTel);
    }

    private void setListener() {
        wrongInfoBack.setOnClickListener(this);
        wrongInfoSubmit.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.wrongInfoBack: onBackPressed(); break;
            case R.id.wrongInfoSubmit:
                WrongInfo wrongInfo = makeWrongInfo();
                if(!isValid(wrongInfo)) return;
                if(!NetworkUtil.isConnected(this)) return;
                wrongInfoService.createWrongInfo(wrongInfo);
                break;
        }
    }


    private WrongInfo makeWrongInfo() {
        WrongInfo wrongInfo = new WrongInfo();
        wrongInfo.title = wrongInfoCouponNameDetail.getText().toString();
        wrongInfo.content = wrongInfoContentDetail.getText().toString();
        wrongInfo.email = wrongInfoCouponEmailDetail.getText().toString();
        wrongInfo.tel = wrongInfoTelDetail.getText().toString();
        wrongInfo.parentNo = parentNo;
        return wrongInfo;
    }

    private boolean isValid(WrongInfo info){
        if (StringUtil.isEmpty(info.content)) {
            NoticeUtil.makeToast(WrongInfoActivity.this, "내용을 입력해주세요");
            return false;
        }
        return true;
    }

}

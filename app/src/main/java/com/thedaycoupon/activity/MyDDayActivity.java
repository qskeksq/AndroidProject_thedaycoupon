package com.thedaycoupon.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedaycoupon.R;
import com.thedaycoupon.util.StringUtil;

public class MyDDayActivity extends BaseActivity {

    private ImageView myDDayBack;
    private TextView myDDayTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_day);
        init();
    }

    private void init(){
        initView();
        setFont();
        setListener();
    }

    private void initView() {
        myDDayBack = findViewById(R.id.myDDayBack);
        myDDayTitle = findViewById(R.id.myDDayTitle);
    }

    private void setFont() {
        StringUtil.setTypefaceNanumL(this, myDDayTitle);
    }

    private void setListener(){
        myDDayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}

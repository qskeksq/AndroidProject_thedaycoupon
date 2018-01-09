package com.thedaycoupon.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.thedaycoupon.R;
import com.thedaycoupon.adapter.IntroPagerAdapter;

/**
 * 소개 액티비티
 */
public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        setPager();
    }

    private void setPager() {
        ViewPager introPager = findViewById(R.id.introPager);
        IntroPagerAdapter adapter = new IntroPagerAdapter();
        introPager.setAdapter(adapter);
    }
}

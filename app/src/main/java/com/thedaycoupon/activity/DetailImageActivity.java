package com.thedaycoupon.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.thedaycoupon.R;
import com.thedaycoupon.adapter.DetailImagePagerAdapter2;
import com.thedaycoupon.util.Const;

public class DetailImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        ViewPager detailImagePager2 = findViewById(R.id.detailImagePager2);
        String[] urls = getIntent().getStringArrayExtra(Const.GOUTIL_EXTRA_1);
        DetailImagePagerAdapter2 adapter2 = new DetailImagePagerAdapter2(urls);
        detailImagePager2.setAdapter(adapter2);
    }
}

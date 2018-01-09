package com.thedaycoupon.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.DetailService;
import com.thedaycoupon.adapter.DetailImagePagerAdapter;
import com.thedaycoupon.custom.WorkaroundMapView;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.detailImage.DetailImage;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.DetailUtil;
import com.thedaycoupon.util.GoUtil;
import com.thedaycoupon.util.StringUtil;

import java.util.List;

public class DetailActivity extends BaseActivity
        implements DetailService.IDetailService, View.OnClickListener, OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private String TAG = getClass().getSimpleName();

    private Button detailWrongInfo;
    private ScrollView detailScrollView;
    private ViewPager detailImagePager;
    private ImageView detailBackButton, detailCouponImage, detailGo;
    private TextView detailCouponExp, detailCouponDiscount, detailGoTxt, detailPeriod, detailPeriodDetail, detailInfo, detailInfoDetail, detailParticipate, detailParticipateDetail, detailNotice, detailNoticeDetail, detailBusinessHour, detailBusinessHourDetail, detailHomepage, detailHomepageDetail, detailPhone, detailPhoneDetail, detailExtraInfo, detailTitle;
    private Coupon mCoupon;
    private WorkaroundMapView detailMapview;
    private DetailImagePagerAdapter detailImagePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        detailMapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        detailMapview.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detailMapview.onDestroy();
    }

    private void init(Bundle sis) {
        getIntentExtra();
        initView();
        setFont();
        setListener();
        setMapView(sis);
        setPager();
        getDetailImage();
        setCouponInfo();
        setMainInfo();
    }

    private void getDetailImage(){
        DetailService service = new DetailService(this, this);
        service.loadDetailImage(mCoupon.no);
    }

    private void getIntentExtra() {
        int no = getIntent().getIntExtra(Const.GOUTIL_EXTRA_1, 0);
        mCoupon = CouponDao.getInstance(this).readByValue(Const.COLUMN_NO, no).get(0);
    }

    private void initView() {
        detailScrollView = findViewById(R.id.detailScrollView);
        detailImagePager = findViewById(R.id.detailImagePager);
        detailCouponImage = findViewById(R.id.detailCouponImage);
        detailCouponExp = findViewById(R.id.detailCouponExp);
        detailCouponDiscount = findViewById(R.id.detailCouponDiscount);
        detailGo = findViewById(R.id.detailGo);
        detailGoTxt = findViewById(R.id.detailGoTxt);
        detailPeriod = findViewById(R.id.detailPeriod);
        detailPeriodDetail = findViewById(R.id.detailPeriodDetail);
        detailInfo = findViewById(R.id.detailInfo);
        detailInfoDetail = findViewById(R.id.detailInfoDetail);
        detailParticipate = findViewById(R.id.detailParticipate);
        detailParticipateDetail = findViewById(R.id.detailParticipateDetail);
        detailNotice = findViewById(R.id.detailNotice);
        detailNoticeDetail = findViewById(R.id.detailNoticeDetail);
        detailBusinessHour = findViewById(R.id.detailBusinessHour);
        detailBusinessHourDetail = findViewById(R.id.detailBusinessHourDetail);
        detailHomepage = findViewById(R.id.detailHomepage);
        detailHomepageDetail = findViewById(R.id.detailHomepageDetail);
        detailPhone = findViewById(R.id.detailPhone);
        detailPhoneDetail = findViewById(R.id.detailPhoneDetail);
        detailExtraInfo = findViewById(R.id.detailExtraInfo);
        detailTitle = findViewById(R.id.detailTitle);
        detailWrongInfo = findViewById(R.id.detailWrongInfo);
        detailBackButton = findViewById(R.id.detailBackButton);
        detailMapview = findViewById(R.id.detailMapView);
    }

    private void setFont() {
        StringUtil.setTypefaceNanumL(this, detailCouponDiscount);
        StringUtil.setTypefaceNanumL(this, detailCouponExp);
        StringUtil.setTypefaceNanumR(this, detailPeriod);
        StringUtil.setTypefaceNanumR(this, detailInfo);
        StringUtil.setTypefaceNanumR(this, detailParticipate);
        StringUtil.setTypefaceNanumR(this, detailGoTxt);
        StringUtil.setTypefaceNanumR(this, detailBusinessHour);
        StringUtil.setTypefaceNanumR(this, detailHomepage);
        StringUtil.setTypefaceNanumR(this, detailPhone);
        StringUtil.setTypefaceNanumR(this, detailWrongInfo);
        StringUtil.setTypefaceNanumR(this, detailNotice);
    }

    private void setListener() {
        detailWrongInfo.setOnClickListener(this);
        detailGo.setOnClickListener(this);
        detailBackButton.setOnClickListener(this);
        detailMapview.setListener(touchListener);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detailWrongInfo:
                GoUtil.startActivity(v.getContext(), WrongInfoActivity.class, mCoupon.name, mCoupon.no);
                break;
            case R.id.detailGo:
                goEventPage();
                break;
            case R.id.detailBackButton:
                onBackPressed();
                break;
        }
    }

    WorkaroundMapView.OnTouchListener touchListener = new WorkaroundMapView.OnTouchListener() {
        @Override
        public void onTouch() {
            detailScrollView.requestDisallowInterceptTouchEvent(true);
        }
    };

    private void setMapView(Bundle savedInstanceState) {
        detailMapview.onCreate(savedInstanceState);
        detailMapview.getMapAsync(this);
    }

    private void setPager() {
        detailImagePagerAdapter = new DetailImagePagerAdapter();
        detailImagePager.setAdapter(detailImagePagerAdapter);
    }

    private void setCouponInfo() {
        Glide.with(this).load(getFileStreamPath(mCoupon.logoFilename)).into(detailCouponImage);
        detailCouponExp.setText(mCoupon.name);
        detailCouponDiscount.setText(mCoupon.discountRate);
    }

    private void setMainInfo() {
        setTitle();
        setPeriod();
        setInfo();
        setParticipate();
        setNotice();
    }

    private void setTitle() {
        detailTitle.setText(mCoupon.title);
    }

    private void setPeriod() {
        String startDate = StringUtil.sdf(mCoupon.startDate);
        String endDate = StringUtil.sdf(mCoupon.endDate);
        detailPeriodDetail.setText(DetailUtil.appendPeriod(startDate, endDate));
    }

    private void setInfo() {
        if (!DetailUtil.isEmpty(mCoupon.info))
            detailInfoDetail.setText(DetailUtil.splitInfo(mCoupon.info, ""));
    }

    private void setParticipate() {
        if (!DetailUtil.isEmpty(mCoupon.participate))
            detailParticipateDetail.setText(DetailUtil.splitInfo(mCoupon.participate, ""));
    }

    private void setNotice() {
        if (!DetailUtil.isEmpty(mCoupon.notice))
            detailNoticeDetail.setText(DetailUtil.splitInfo(mCoupon.notice, "* "));
    }

    private void goEventPage() {
        if (!DetailUtil.isEmpty(mCoupon.homepage)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mCoupon.homepage));
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!DetailUtil.hasLocation(mCoupon.longitude, mCoupon.latitude)) return;
        setMapVisible();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLng(mCoupon.latitude, mCoupon.longitude), 15));
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        GoUtil.startActivity(this, LocationActivity.class, mCoupon.latitude, mCoupon.longitude);
    }

    private void setMapVisible() {
        detailMapview.setVisibility(View.VISIBLE);
    }

    private LatLng getLatLng(double lat, double lng) {
        return new LatLng(lat, lng);
    }

    @Override
    public void setImagePagerGone() {
        detailImagePager.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<DetailImage> imageList) {
        detailImagePager.setVisibility(View.VISIBLE);
        detailImagePagerAdapter.setData(imageList);
    }

}

package com.thedaycoupon.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.model.LatLng;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.RequestService;
import com.thedaycoupon.adapter.ImageAdapter;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.util.BitmapUtil;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.DialogUtil;
import com.thedaycoupon.util.FileUtil;
import com.thedaycoupon.util.GoUtil;
import com.thedaycoupon.util.NetworkUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PermissionLocationUtil;
import com.thedaycoupon.util.RequestUtil;
import com.thedaycoupon.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.thedaycoupon.R.drawable.remove;

public class RequestCouponActivity extends BaseActivity
        implements PermissionLocationUtil.LocationPermissionCallBack, View.OnClickListener,
                    DialogUtil.IArrayDialog, DialogUtil.ISimpleDialog{

    private TextView requestTitle;
    private TextView requestCouponTitle;
    private TextView requestPeriod;
    private TextView requestInfo;
    private TextView requestParticipate;
    private TextView requestLogoFile;
    private TextView requestAddress;
    private TextView requestHomepage;
    private TextView requestPrerequisiteInfo;
    private TextView requestBrandName;
    private TextView requestPalceInfo;
    private TextView requestBusinessHour;
    private TextView requestExtraInfo;
    private TextView requestTel;
    private TextView requestNotice;
    private TextView requestPhoto;
    private TextView requestCouponExp;
    private TextView requestCouponDiscountRate;
    private EditText requestCouponTitleDetail;
    private EditText requestPeriodStart;
    private EditText requestPeriodEnd;
    private EditText requestInfoDetail;
    private EditText requestParticipateDetail;
    private EditText requestLogoFileDetail;
    private EditText requestLogoFileAttach;
    private EditText requestAddressDetail;
    private EditText requestHomepageDetail;
    private EditText requestBrandNameDetail;
    private EditText requestBusinessHourDetail;
    private EditText requestTelDetail;
    private EditText requestNoticeDetail;
    private EditText requestDDayDetail;
    private EditText requestDiscountRateDetail;
    private EditText requestBusinessHourWeekadayDetail;
    private EditText requestBusinessHourSatDetail;
    private EditText requestBusinessHourSunDetail;
    private EditText requestBusinessHourSunDetailEnd;
    private EditText requestBusinessHourSatDetailEnd;
    private EditText requestBusinessHourWeekadayDetailEnd;
    private EditText requestDiscountRateEvent;
    private EditText requestDDayMainCategory;
    private EditText requestDDaySubCategory;
    private EditText requestTargetDetail;
    private ImageView requestBack;
    private ImageView requestInfoAdd;
    private ImageView requestParticipateAdd;
    private ImageView requestNoticeAdd;
    private ImageView requestPhotoAdd;
    private ImageView requestCouponLogo;
    private LinearLayout layoutCouponInfo;
    private LinearLayout innerLayoutCouponInfo;
    private LinearLayout layoutCouponParticipate;
    private LinearLayout innerLayoutCouponParticipate;
    private LinearLayout layoutCouponNotice;
    private LinearLayout innerLayoutCouponNotice;
    private ConstraintLayout requestPreview;
    private Switch requestPreviewSwitch;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Button loadCoupon;
    private RecyclerView requestImageRecycler;
    private View timePickerView, datePickerView;

    private ImageAdapter imageAdapter;
    private List<View> infoViews;
    private List<View> participateViews;
    private List<View> noticeViews;
    private List<File> imageFiles;
    private File logoFile;
    private Coupon mCoupon;
    private String logoFileName;
    private File imageFile;
    private boolean isSaving;
    private int mainCategoryPosition;
    private boolean eventOn, isFirstInfo, isFirstParticipateInfo, isFirstNoticeInfo;
    private AlertDialog.Builder datePickerBuilder, timePickerBuilder;
    private RequestService requestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_coupon);
        init();
    }

    private void init(){
        initView();
        initData();
        setFont();
        setListener();
        setRecycler();
        mCoupon = new Coupon();
        requestService = new RequestService(this);
    }

    private void initView() {
        requestBack =  findViewById(R.id.requestBack);
        requestTitle =  findViewById(R.id.requestTitle);
        requestCouponTitle =  findViewById(R.id.requestCouponTitle);
        requestCouponTitleDetail =  findViewById(R.id.requestCouponTitleDetail);
        requestPeriod =  findViewById(R.id.requestPeriod);
        requestPeriodStart =  findViewById(R.id.requestPeriodStart);
        requestPeriodEnd =  findViewById(R.id.requestPeriodEnd);
        requestInfo =  findViewById(R.id.requestInfo);
        requestInfoDetail =  findViewById(R.id.requestInfoDetail);
        requestInfoAdd =  findViewById(R.id.requestInfoAdd);
        requestParticipate =  findViewById(R.id.requestParticipate);
        requestParticipateDetail =  findViewById(R.id.requestParticipateDetail);
        requestParticipateAdd =  findViewById(R.id.requestParticipateAdd);
        requestLogoFile =  findViewById(R.id.requestLogoFile);
        requestLogoFileDetail =  findViewById(R.id.requestLogoFileDetail);
        requestLogoFileAttach =  findViewById(R.id.requestLogoFileAttach);
        requestAddress =  findViewById(R.id.requestAddress);
        requestAddressDetail =  findViewById(R.id.requestAddressDetail);
        requestHomepage =  findViewById(R.id.requestHomepage);
        requestHomepageDetail =  findViewById(R.id.requestHomepageDetail);
        requestPrerequisiteInfo =  findViewById(R.id.requestPrerequisiteInfo);
        requestBrandName =  findViewById(R.id.requestBrandName);
        requestBrandNameDetail =  findViewById(R.id.requestBrandNameDetail);
        requestPalceInfo =  findViewById(R.id.requestPalceInfo);
        requestBusinessHour =  findViewById(R.id.requestBusinessHour);
        requestBusinessHourDetail =  findViewById(R.id.requestBusinessHourDetail);
        requestExtraInfo =  findViewById(R.id.requestExtraInfo);
        requestTel =  findViewById(R.id.requestTel);
        requestTelDetail =  findViewById(R.id.requestTelDetail);
        requestNotice =  findViewById(R.id.requestNotice);
        requestNoticeDetail =  findViewById(R.id.requestNoticeDetail);
        requestNoticeAdd =  findViewById(R.id.requestNoticeAdd);
        requestPhoto =  findViewById(R.id.requestPhoto);
        requestPhotoAdd =  findViewById(R.id.requestPhotoAdd);
        layoutCouponInfo =  findViewById(R.id.layoutCouponInfo);
        innerLayoutCouponInfo =  findViewById(R.id.innerLayoutCouponInfo);
        layoutCouponParticipate =  findViewById(R.id.layoutCouponParticipate);
        innerLayoutCouponParticipate =  findViewById(R.id.innerLayoutCouponParticipate);
        layoutCouponNotice =  findViewById(R.id.layoutCouponNotice);
        innerLayoutCouponNotice =  findViewById(R.id.innerLayoutCouponNotice);
        requestDDayDetail =  findViewById(R.id.requestDDayDetail);
        requestDiscountRateDetail =  findViewById(R.id.requestDiscountRateDetail);
        requestPreview =  findViewById(R.id.requestPreview);
        requestCouponLogo =  findViewById(R.id.requestCouponLogo);
        requestCouponExp =  findViewById(R.id.requestCouponExp);
        requestCouponDiscountRate =  findViewById(R.id.requestCouponDiscountRate);
        requestPreviewSwitch =  findViewById(R.id.requestPreviewSwitch);
        requestDiscountRateEvent =  findViewById(R.id.requestDiscountRateEvent);
        requestImageRecycler =  findViewById(R.id.requestImageRecycler);
        loadCoupon =  findViewById(R.id.loadCoupon);
        requestDDayMainCategory =  findViewById(R.id.requestDDayMainCategory);
        requestDDaySubCategory =  findViewById(R.id.requestDDaySubCategory);
        requestTargetDetail =  findViewById(R.id.requestTargetDetail);
        datePickerView = getLayoutInflater().inflate(R.layout.dialog_datepick, null);
        datePicker = datePickerView.findViewById(R.id.datePicker);
        timePickerView = getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
        timePicker = timePickerView.findViewById(R.id.timePicker);
        requestBusinessHourWeekadayDetail = timePickerView.findViewById(R.id.requestBusinessHourWeekadayDetail);
        requestBusinessHourSatDetail = timePickerView.findViewById(R.id.requestBusinessHourSatDetail);
        requestBusinessHourSunDetail = timePickerView.findViewById(R.id.requestBusinessHourSunDetail);
        requestBusinessHourWeekadayDetailEnd = timePickerView.findViewById(R.id.requestBusinessHourWeekadayDetailEnd);
        requestBusinessHourSatDetailEnd = timePickerView.findViewById(R.id.requestBusinessHourSatDetailEnd);
        requestBusinessHourSunDetailEnd = timePickerView.findViewById(R.id.requestBusinessHourSunDetailEnd);
        timePickerBuilder = new AlertDialog.Builder(this);
        datePickerBuilder = new AlertDialog.Builder(this);
    }

    private void initData(){
        infoViews = new ArrayList<>();
        participateViews = new ArrayList<>();
        noticeViews = new ArrayList<>();
        imageFiles = new ArrayList<>();
    }

    private void setFont() {
        StringUtil.setTypefaceNanumL(this, requestTitle);
    }

    private void setListener() {

        // 뒤로가기
        requestBack.setOnClickListener(this);

        // 미리보기 스위치
        requestPreviewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCouponPreview(isChecked);
            }
        });

        // 기념일 선택
        requestDDayDetail.setOnClickListener(this);

        // 메인카테고리 선택
        requestDDayMainCategory.setOnClickListener(this);

        // 서브 카테고리 선택
        requestDDaySubCategory.setOnClickListener(this);

        // 브랜드명
        requestBrandNameDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestCouponExp.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 이미지 파일 첨부
        requestLogoFileAttach.setOnClickListener(this);

        // 할인율
        requestDiscountRateDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestCouponDiscountRate.setText(s.toString()+"%");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 이벤트 선택
        requestDiscountRateEvent.setOnClickListener(this);

        // 시작날짜 선택
        requestPeriodStart.setOnClickListener(this);

        // 끝날짜 선택
        requestPeriodEnd.setOnClickListener(this);

        // 할인정보 추가
        requestInfoAdd.setOnClickListener(this);

        // 참여방법 추가
        requestParticipateAdd.setOnClickListener(this);

        // 유의사항 추가
        requestNoticeAdd.setOnClickListener(this);

        // 주소정보
        requestAddressDetail.setOnClickListener(this);

        // 평일 영업 시작시간
        requestBusinessHourWeekadayDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setBHWeekDayStartFocused(hasFocus);
            }
        });

        // 평일 영업 종료시간
        requestBusinessHourWeekadayDetailEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setBHWeekDayEndFocused(hasFocus);
            }
        });

        // 토요일 영업 시작시간
        requestBusinessHourSatDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setBHSatStartFocused(hasFocus);
            }
        });

        // 토요일 영업 종료시간
        requestBusinessHourSatDetailEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setBHSatEndFocused(hasFocus);
            }
        });

        // 일요일 영업 시작시간
        requestBusinessHourSunDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setBHSunStartFocused(hasFocus);
            }
        });

        // 일요일 영업 종료시간
        requestBusinessHourSunDetailEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setBHSunEndFocused(hasFocus);
            }
        });

        // 영업시간 선택
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setBHTime(hourOfDay, minute);
            }
        });

        // 영업시간
        requestBusinessHourDetail.setOnClickListener(this);

        // 이미지 파일
        requestPhotoAdd.setOnClickListener(this);

        // 요청
        loadCoupon.setOnClickListener(this);
    }

    private void setRecycler() {
        imageAdapter = new ImageAdapter(this);
        requestImageRecycler.setAdapter(imageAdapter);
        requestImageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.requestBack:
                showGoBackDialog(R.id.requestBack);
                break;
            case R.id.requestDDayDetail:
                showDDayDialog(R.id.requestDDayDetail);
                break;
            case R.id.requestDDayMainCategory:
                showMainCategoryDialog(R.id.requestDDayMainCategory);
                break;
            case R.id.requestDDaySubCategory:
                showSubCategoryDialog(R.id.requestDDaySubCategory);
                break;
            case R.id.requestPeriodStart:
                showDateDialog(R.id.requestPeriodStart);
                break;
            case R.id.requestPeriodEnd:
                showDateDialog(R.id.requestPeriodEnd);
                break;
            case R.id.requestLogoFileAttach:
                attachLogoFile();
                break;
            case R.id.requestDiscountRateEvent:
                setEvent();
                break;
            case R.id.requestInfoAdd:
                addCouponInfo();
                break;
            case R.id.requestParticipateAdd:
                addParticipateInfo();
                break;
            case R.id.requestNoticeAdd:
                addNoticeInfo();
                break;
            case R.id.requestAddressDetail:
                goFindAddress();
                break;
            case R.id.requestBusinessHourDetail:
                showTimeDialog(R.id.requestBusinessHourDetail);
                break;
            case R.id.requestPhotoAdd:
                attachImageFile();
                break;
            case R.id.loadCoupon:
                sendCoupon();
                break;
        }
    }

    /**
     * 다이얼로그 결과값 처리
     */
    @Override
    public void onSimplePositiveButton(int id) {
        switch (id){
            case R.id.requestBack:
                break;
            case R.id.requestPeriodStart:
                setStartDate();
                break;
            case R.id.requestPeriodEnd:
                setEndDate();
                break;
            case R.id.requestBusinessHourDetail:
                setBusinessHour();
                break;
        }
    }

    @Override
    public void onSimpleNegativeButton(int id) {
        switch (id){
            case R.id.requestBack: finish(); break;
            case R.id.requestPeriodStart: break;
            case R.id.requestPeriodEnd: break;
            case R.id.requestBusinessHour: break;
        }
    }

    @Override
    public void onSimpleCanceled(int id) {
        switch (id){
            case R.id.requestBack: finish(); break;
            case R.id.requestPeriodStart: break;
            case R.id.requestPeriodEnd: break;
            case R.id.requestBusinessHour: break;
        }
    }

    @Override
    public void onItemSelected(int id, int which, String[] array) {
        switch (id){
            case R.id.requestDDayDetail:
                setDDay(array, which);
                break;
            case R.id.requestDDayMainCategory:
                setMainCategory(array, which);
                break;
            case R.id.requestDDaySubCategory:
                setSubCategory(array, which);
                break;
        }
    }

    @Override
    public void onItemCanceled(int id) {

    }

    /**
     * 다이얼로그 show
     */
    private void showGoBackDialog(int id){
        DialogUtil.showDialog(
                this,
                getResources().getString(R.string.dialog_request_cancel),
                getResources().getString(R.string.dialog_request_cancel_positive),
                getResources().getString(R.string.dialog_request_cancel_negative),
                this, id);
    }

    private void showDDayDialog(int id){
        DialogUtil.showDialog(
                this,
                getResources().getString(R.string.request_dday_title),
                getResources().getStringArray(R.array.category),
                this, id);
    }

    private void showMainCategoryDialog(int id){
        DialogUtil.showDialog(
                this,
                getResources().getString(R.string.request_dday_main_category),
                getResources().getStringArray(R.array.request_main_category),
                this, id);
    }

    private void showSubCategoryDialog(int id){
        if(StringUtil.isEmpty(requestDDayMainCategory.getText().toString())){
            NoticeUtil.makeToast(this, "메인 카테고리를 선택해주세요");
            return;
        }
        int resID = getResources().getIdentifier(
                getResources().getStringArray(R.array.sub_category_resid)[mainCategoryPosition],
                "array",
                RequestCouponActivity.this.getPackageName());
        DialogUtil.showDialog(
                this,
                getResources().getString(R.string.request_dday_sub_category),
                getResources().getStringArray(resID),
                this, id);
    }

    private void showDateDialog(int id){
        DialogUtil.showDialog(
                datePickerView,
                datePickerBuilder,
                getResources().getString(R.string.dialog_confirm),
                getResources().getString(R.string.dialog_cancel),
                this, id);
    }

    private void showTimeDialog(int id){
        DialogUtil.showDialog(
                timePickerView,
                timePickerBuilder,
                getResources().getString(R.string.dialog_confirm),
                getResources().getString(R.string.dialog_cancel),
                this, id);
    }

    /**
     * 다이얼로그 확인, 취소 선택시
     */
    private void setDDay(String[] array, int which){
        requestDDayDetail.setText(array[which]);
    }

    private void setMainCategory(String[] array, int which){
        requestDDayMainCategory.setText(array[which]);
        requestDDaySubCategory.setText("");
        mainCategoryPosition = which;
    }

    private void setSubCategory(String[] array, int which){
        requestDDaySubCategory.setText(array[which]);
    }

    private void setStartDate(){
        String startDate = RequestUtil.makeDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        requestPeriodStart.setText(startDate);
    }

    private void setEndDate(){
        String endDate = RequestUtil.makeDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        requestPeriodEnd.setText(endDate);
    }

    private void setBusinessHour(){
        String businessHour =
            "평일 : " +
            requestBusinessHourWeekadayDetail.getText().toString() + "~" +
            requestBusinessHourWeekadayDetailEnd.getText().toString() + "\n" +
            "토요일 : " +
            requestBusinessHourSatDetail.getText().toString() + "~" +
            requestBusinessHourSatDetailEnd.getText().toString() + "\n" +
            "일요일 : " +
            requestBusinessHourSunDetail.getText().toString() + "~" +
            requestBusinessHourSunDetailEnd.getText().toString();
            requestBusinessHourDetail.setText(businessHour);
    }

    /**
     * 나머지 클릭 리스너 콜백 메소드
     */
    private void switchCouponPreview(boolean isChecked){
        if (isChecked) {
            requestPreview.setVisibility(View.VISIBLE);
        } else {
            requestPreview.setVisibility(View.GONE);
        }
    }

    private void pickFromAlbum(int reqCode){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, reqCode);
    }

    private void attachLogoFile(){
        if (RequestUtil.isEmpty(getLogoFileName())) {
            NoticeUtil.makeToast(this, "로고파일명을 입력해주세요");
            return;
        }
        getLogoFile();
        pickFromAlbum(Const.REQ_CODE_PICK_LOGO_FROM_ALBUM);
    }

    private String getLogoFileName(){
        if(RequestUtil.isEmpty(logoFileName))
            logoFileName = requestLogoFileDetail.getText().toString();
        return logoFileName;
    }

    private File getLogoFile(){
        if(logoFile != null) {
            logoFile.delete();
        }
        logoFile = FileUtil.getPNGFile(this, getLogoFileName());
        return logoFile;
    }

    private void attachImageFile(){
        if (StringUtil.isEmpty(getLogoFileName())) {
            NoticeUtil.makeToast(this, "로고파일명을 입력해주세요");
            return;
        }
        getImageFile();
        pickFromAlbum(Const.REQ_CODE_PICK_IMAGE_FROM_ALBUM);
    }

    private String getImageFilename(){
        return getLogoFileName()+"_"+System.currentTimeMillis();
    }

    private File getImageFile(){
        imageFile = FileUtil.getJPEGFile(this, getImageFilename());
        return imageFile;
    }

    private void setEvent(){
        if (eventOn) {
            requestDiscountRateEvent.setText("");
            requestCouponDiscountRate.setText(requestDiscountRateDetail.getText().toString());
            eventOn = false;
        } else {
            requestDiscountRateEvent.setText(getResources().getString(R.string.request_event));
            requestCouponDiscountRate.setText("이벤트");
            eventOn = true;
        }
    }

    private void addCouponInfo(){
        if (isFirstInfo) {
            innerLayoutCouponInfo.setVisibility(View.GONE);
            requestInfoDetail.setText("");
        } else {
            requestInfoAdd.setImageResource(remove);
            addMoreInfoView(layoutCouponInfo, infoViews);
            isFirstInfo = true;
        }
    }

    private void addParticipateInfo(){
        if (isFirstParticipateInfo) {
            innerLayoutCouponParticipate.setVisibility(View.GONE);
            requestParticipateDetail.setText("");
        } else {
            requestParticipateAdd.setImageResource(remove);
            addMoreInfoView(layoutCouponParticipate, participateViews);
            isFirstParticipateInfo = true;
        }
    }

    private void addNoticeInfo(){
        if (isFirstNoticeInfo) {
            innerLayoutCouponNotice.setVisibility(View.GONE);
            requestNoticeDetail.setText("");
        } else {
            requestNoticeAdd.setImageResource(remove);
            addMoreInfoView(layoutCouponNotice, noticeViews);
            isFirstNoticeInfo = true;
        }
    }

    // 위치
    private void goFindAddress(){
        PermissionLocationUtil.getInstance().checkVersion(RequestCouponActivity.this, RequestCouponActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionLocationUtil.getInstance().onResult(grantResults, this);
    }

    @Override
    public void postLocationPermission() {
        GoUtil.startActivityForResult(this, FindPlaceActivity.class, Const.REQ_CODE_FIND_PLACE);
    }

    // 영업시간
    private void setBHWeekDayStartFocused(boolean hasFocus){
        if (hasFocus) {
            requestBusinessHourWeekadayDetail.setBackgroundResource(R.drawable.plain_back_red);
        } else {
            requestBusinessHourWeekadayDetail.setBackgroundResource(R.drawable.plain_back);
        }
    }

    private void setBHWeekDayEndFocused(boolean hasFocus){
        if (hasFocus) {
            requestBusinessHourWeekadayDetailEnd.setBackgroundResource(R.drawable.plain_back_red);
        } else {
            requestBusinessHourWeekadayDetailEnd.setBackgroundResource(R.drawable.plain_back);
        }
    }

    private void setBHSatStartFocused(boolean hasFocus){
        if (hasFocus) {
            requestBusinessHourSatDetail.setBackgroundResource(R.drawable.plain_back_red);
        } else {
            requestBusinessHourSatDetail.setBackgroundResource(R.drawable.plain_back);
        }
    }

    private void setBHSatEndFocused(boolean hasFocus){
        if (hasFocus) {
            requestBusinessHourSatDetailEnd.setBackgroundResource(R.drawable.plain_back_red);
        } else {
            requestBusinessHourSatDetailEnd.setBackgroundResource(R.drawable.plain_back);
        }
    }

    private void setBHSunStartFocused(boolean hasFocus){
        if (hasFocus) {
            requestBusinessHourSunDetail.setBackgroundResource(R.drawable.plain_back_red);
        } else {
            requestBusinessHourSunDetail.setBackgroundResource(R.drawable.plain_back);
        }
    }

    private void setBHSunEndFocused(boolean hasFocus){
        if (hasFocus) {
            requestBusinessHourSunDetailEnd.setBackgroundResource(R.drawable.plain_back_red);
        } else {
            requestBusinessHourSunDetailEnd.setBackgroundResource(R.drawable.plain_back);
        }
    }

    private void setBHTime(int hourOfDay, int minute){
        String businessHour = hourOfDay + ":" + minute;
        if (requestBusinessHourWeekadayDetail.hasFocus()) {
            requestBusinessHourWeekadayDetail.setText(businessHour);
        } else if (requestBusinessHourSatDetail.hasFocus()) {
            requestBusinessHourSatDetail.setText(businessHour);
        } else if (requestBusinessHourSunDetail.hasFocus()) {
            requestBusinessHourSunDetail.setText(businessHour);
        } else if (requestBusinessHourWeekadayDetailEnd.hasFocus()) {
            requestBusinessHourWeekadayDetailEnd.setText(businessHour);
        } else if (requestBusinessHourSatDetailEnd.hasFocus()) {
            requestBusinessHourSatDetailEnd.setText(businessHour);
        } else if (requestBusinessHourSunDetailEnd.hasFocus()) {
            requestBusinessHourSunDetailEnd.setText(businessHour);
        }
    }

    private void addMoreInfoView(final ViewGroup parent, final List<View> viewRepo) {
        final View view = getLayoutInflater().inflate(R.layout.item_add, parent, false);
        final ImageView addButton = view.findViewById(R.id.itemAddViewImg);
        addButton.setTag(false);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((boolean) v.getTag() == true) {
                    parent.removeView(view);
                    viewRepo.remove(view);
                } else {
                    addButton.setImageResource(remove);
                    addMoreInfoView(parent, viewRepo);
                    v.setTag(true);
                }
            }
        });
        viewRepo.add(view);
        parent.addView(view);
    }

    /**
     * 쿠폰 생성
     */
    private Coupon makeCoupon() {
        mCoupon.theDay = requestDDayDetail.getText().toString();
        mCoupon.mainCategory = requestDDayMainCategory.getText().toString();
        mCoupon.subCategory = requestDDaySubCategory.getText().toString();
        mCoupon.name = requestBrandNameDetail.getText().toString();
        mCoupon.discountRate = getDiscountRate();
        mCoupon.title = requestCouponTitleDetail.getText().toString();
        mCoupon.target = requestTargetDetail.getText().toString();
        mCoupon.startDate = requestPeriodStart.getText().toString();
        mCoupon.endDate = requestPeriodEnd.getText().toString();
        mCoupon.info = appendInfoBySep();
        mCoupon.participate = appendParticipateBySep();
        mCoupon.address = requestAddressDetail.getText().toString();
        mCoupon.homepage = requestHomepageDetail.getText().toString();
        mCoupon.businessHour = requestBusinessHourDetail.getText().toString();
        mCoupon.tel = requestTelDetail.getText().toString();
        mCoupon.notice = appendNoticeBySep();
        return mCoupon;
    }

    private boolean isValidRequest() {
        if (RequestUtil.isEmpty(requestDDayDetail.getText().toString())) {
            NoticeUtil.makeToast(this, "기념일을 선택해주세요");
            return false;
        }
        if (RequestUtil.isEmpty(requestDDayMainCategory.getText().toString()) ||
                RequestUtil.isEmpty(requestDDaySubCategory.getText().toString())) {
            NoticeUtil.makeToast(this, "카테고리를 선택해주세요");
            return false;
        }
        if (RequestUtil.isEmpty(requestBrandNameDetail.getText().toString())) {
            NoticeUtil.makeToast(this, "브랜드명을 입력해주세요");
            return false;
        }
        if (RequestUtil.isEmpty(requestDiscountRateEvent.getText().toString()) &&
                RequestUtil.isEmpty(requestDiscountRateEvent.getText().toString())) {
            NoticeUtil.makeToast(this, "할인율을 작성해주세요");
            return false;
        }
        if (RequestUtil.isEmpty(requestCouponTitleDetail.getText().toString())) {
            NoticeUtil.makeToast(this, "제목을 입력해주세요");
            return false;
        }
        if (RequestUtil.isEmpty(requestPeriodStart.getText().toString()) ||
                RequestUtil.isEmpty(requestPeriodEnd.getText().toString())) {
            NoticeUtil.makeToast(this, "기간을 작성해주세요");
            return false;
        }

        if (RequestUtil.isEmpty(requestInfoDetail.getText().toString()) &&
                RequestUtil.isEmpty(appendInfo())) {
            NoticeUtil.makeToast(this, "할인정보를 입력해주세요");
            return false;
        }

        if (RequestUtil.isEmpty(requestParticipateDetail.getText().toString()) &&
                RequestUtil.isEmpty(appendParticipate())) {
            NoticeUtil.makeToast(this, "참여방법을 입력해주세요");
            return false;
        }

        if (StringUtil.isEmpty(requestAddressDetail.getText().toString()) &&
                StringUtil.isEmpty(requestHomepageDetail.getText().toString())) {
            NoticeUtil.makeToast(this, "적용장소가 없습니다");
            return false;
        }
        return true;
    }

    private String appendInfo(){
        String info = "";
        for (View view : infoViews) {
            info += ((EditText) ((ViewGroup) view).getChildAt(0)).getText().toString();
        }
        return info;
    }

    private String appendInfoBySep(){
        String info = "";
        info += requestInfoDetail.getText().toString();
        for (int i = 0; i < infoViews.size(); i++) {
            info += ((EditText) ((ViewGroup) infoViews.get(i)).getChildAt(0)).getText().toString();
            if (infoViews.size() - 1 != i) info += "|";
        }
        return info;
    }

    private String appendParticipate(){
        String participate = "";
        for (View view : participateViews) {
            participate += ((EditText) ((ViewGroup) view).getChildAt(0)).getText().toString();
        }
        return participate;
    }

    private String appendParticipateBySep(){
        String participate = "";
        participate += requestParticipateDetail.getText().toString();
        for (int i = 0; i < participateViews.size(); i++) {
            participate += ((EditText) ((ViewGroup) participateViews.get(i)).getChildAt(0)).getText().toString();
            if (participateViews.size() - 1 != i) participate += "|";
        }
        return participate;
    }

    private String appendNoticeBySep(){
        String notice = "";
        notice += requestNoticeDetail.getText().toString();
        for (int i = 0; i < noticeViews.size(); i++) {
            notice += ((EditText) ((ViewGroup) noticeViews.get(i)).getChildAt(0)).getText().toString();
            if (noticeViews.size() - 1 != i) notice += "|";
        }
        return notice;
    }

    private String getDiscountRate(){
        if(RequestUtil.isEmpty(requestDiscountRateDetail.getText().toString())) return "이벤트";
        return requestDiscountRateDetail.getText().toString();

    }

    private void sendCoupon() {
        // 1, 유효성 확인
        if (!isValidRequest()) {
            return;
        }
        // 2. 사진이 저장중인지 확인
        if (isSaving) {
            NoticeUtil.makeToast(this, "이미지를 저장중입니다");
            return;
        }
        // 네트워크 확인
        if(!NetworkUtil.isConnected(this)) return;
        // 3. 쿠폰 만들기
        makeCoupon();
        // 4. 쿠폰, 로고, 이미지
        requestService.prepare(mCoupon, logoFile, imageFiles);
        // 5. POST
        requestService.loadRequest();
        // 6.
        finish();
        NoticeUtil.makeToast(this, "완료되었습니다. 감사합니다");
    }

    // 로고, 이미지, 위치정보
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.REQ_CODE_PICK_LOGO_FROM_ALBUM && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Glide.with(this).load(uri).asBitmap().into(logoTarget);
        } else if (requestCode == Const.REQ_CODE_PICK_IMAGE_FROM_ALBUM && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            this.uri = uri;
            Glide.with(this).load(uri).asBitmap().into(imageTarget);
        } else if (requestCode == Const.REQ_CODE_FIND_PLACE && resultCode == RESULT_OK) {
            LatLng latLng = data.getParcelableExtra(Const.SET_RESULT_EXTRA1);
            String address = data.getStringExtra(Const.SET_RESULT_EXTRA2);
            setLocationInfo(latLng, address);
        }
    }

    Uri uri;

    SimpleTarget<Bitmap> logoTarget = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
            BitmapUtil.saveBitmapToFilePNGThread(logoBitmapHandler, logoFile, bitmap);
        }
    };

    SimpleTarget<Bitmap> imageTarget = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
            isSaving = true;
            BitmapUtil.saveBitmapToFileJPEGThread(imageBitmapHandler, imageFile, bitmap);
        }
    };

    // 로고
    private Handler logoBitmapHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isSaving = false;
            mCoupon.logoFilename = logoFileName;
            Glide.with(RequestCouponActivity.this)
                .load(logoFile)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(requestCouponLogo);
        }
    };

    // 이미지
    private Handler imageBitmapHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            imageAdapter.addData(uri);
            imageFiles.add(imageFile);
            isSaving = false;
        }
    };

    public void setRecyclerInvisible() {
        requestImageRecycler.setVisibility(View.GONE);
    }

    public void deleteImageFile(int position) {
        imageFiles.remove(position);
    }

    // 위치정보
    private void setLocationInfo(LatLng latLng, String address){
        mCoupon.latitude = latLng.latitude;
        mCoupon.longitude = latLng.longitude;
        mCoupon.address = address;
        requestAddressDetail.setText(mCoupon.address);
    }

    @Override
    public void onBackPressed() {
        DialogUtil.onBackPressed(this);
    }






}

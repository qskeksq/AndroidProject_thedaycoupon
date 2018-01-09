package com.thedaycoupon.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.MainService;
import com.thedaycoupon.adapter.CouponAdapter;
import com.thedaycoupon.custom.customTabLayout.CustomTabIndicatorLayout;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.DeviceInfoUtil;
import com.thedaycoupon.util.DialogUtil;
import com.thedaycoupon.util.GeoUtil;
import com.thedaycoupon.util.GoUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PermissionLocationUtil;
import com.thedaycoupon.util.PreferenceUtil;
import com.thedaycoupon.util.SignInUtil;
import com.thedaycoupon.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thedaycoupon.R.menu.main;


/**
 * 쿠폰리스트 메인화면
 */
public class MainActivity extends BaseActivity
        implements OnMapReadyCallback, PermissionLocationUtil.LocationPermissionCallBack,
                    CouponAdapter.ICouponAdapter, View.OnClickListener, DialogUtil.ISimpleDialog,
                    DialogUtil.IArrayDialog{

    private Toolbar mainToolbar;
    private RecyclerView couponRecycler;
    private CustomTabIndicatorLayout topTabLayout;
    private CustomTabIndicatorLayout bottomTabLayout;
    private MapView couponMapView;
    private TextView textNoData;
    private TextView naviLogin;
    private TextView naviClose;
    private TextView naviTextMyDDay;
    private TextView naviTextRequest;
    private TextView naviTextMyCoupon;
    private TextView naviName;
    private ImageView mainNaviImg;
    private ImageView naviProfileImage;
    private ImageView naviImageBack;
    private DrawerLayout drawerLayout;
    private ConstraintLayout naviMenuMyDDay;
    private ConstraintLayout naviMenuRequest;
    private ConstraintLayout naviMenuMyCoupon;
    private ConstraintLayout naviMenuProfile;

    private int currentPosition;
    private CouponAdapter adapter;
    private GoogleApiClient mGoogleApiClient;
    private Resources resources;
    private CouponDao couponDao;
    private String[] mainCategoryArray, categoryArray;
    private List<Coupon> totalList, ticketList, travelList, foodList, shoppingList, fashionList,
                         beautyList, sportsList, funList, studyList, etcList;
    private boolean totalHaschange, ticketHasChange, travelHasChange, foodHasChange, shoppingHasChange,
                    fashionHasChange, beautyHasChange, sportsHasChange, funHasChange, studyHasChange, etcHasChange;
    private Map<String, List<Coupon>> categorizedListMap;
    private Map<String, Boolean> hasChangeMap;
    private final String TAG = getClass().getSimpleName();
    private MainService mainService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void init(Bundle sis) {
        initView();
        initData();
        setListener();
        setFont();
        setToolbar();
        setTopTabLayout();
        setBottomTabLayout();
        setCouponRecycler();
        setCurrentData(0);
        setNaviProfile();
        setMapView(sis);
        setGoogleApiClient();
    }

    private void initView() {
        couponRecycler = findViewById(R.id.couponRecycler);
        mainToolbar = findViewById(R.id.mainToolbar);
        topTabLayout = findViewById(R.id.topTabLayout);
        bottomTabLayout = findViewById(R.id.bottomTabLayout);
        couponMapView = findViewById(R.id.couponMapView);
        textNoData = findViewById(R.id.textNoData);
        drawerLayout = findViewById(R.id.drawer_layout);
        mainNaviImg = findViewById(R.id.mainNaviImg);
        naviProfileImage = findViewById(R.id.naviProfileImage);
        naviLogin = findViewById(R.id.naviLogin);
        naviClose = findViewById(R.id.naviClose);
        naviMenuMyDDay = findViewById(R.id.naviMenuMyDDay);
        naviMenuRequest = findViewById(R.id.naviMenuRequest);
        naviMenuMyCoupon = findViewById(R.id.naviMenuMyCoupon);
        naviTextMyDDay = findViewById(R.id.naviTextMyDDay);
        naviTextRequest = findViewById(R.id.naviTextRequest);
        naviTextMyCoupon = findViewById(R.id.naviTextMyCoupon);
        naviImageBack = findViewById(R.id.naviImageBack);
        naviMenuProfile = findViewById(R.id.naviMenuProfile);
        naviName = findViewById(R.id.naviName);
    }

    private void setFont() {
        StringUtil.setTypefaceNanumL(this, naviTextMyCoupon);
        StringUtil.setTypefaceNanumL(this, naviTextMyDDay);
        StringUtil.setTypefaceNanumL(this, naviTextRequest);
    }

    private void setToolbar() {
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("");
    }

    private void setTopTabLayout() {
        String[] mainCategory = getMainCategoryArray();
        for (int i = 0; i < mainCategory.length; i++) {
            topTabLayout.addTab(topTabLayout.newTab().setText(mainCategory[i]));
        }
    }

    private void setBottomTabLayout() {
        String[] category = getCategoryArray();
        for (int i = 0; i < category.length; i++) {
            bottomTabLayout.addTab(bottomTabLayout.newTab().setText(category[i]));
            TextView tv1 = (TextView) (((LinearLayout) ((LinearLayout) bottomTabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1));
            tv1.setScaleY(-1);
        }
    }

    private void setCouponRecycler() {
        mainService = new MainService(this);
        adapter = new CouponAdapter(this, this, mainService);
        couponRecycler.setAdapter(adapter);
        couponRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setNaviProfile() {
        if (SignInUtil.hasSignedIn(PreferenceUtil.getString(this, Const.PREF_KEY_ID))) {
            naviName.setVisibility(View.VISIBLE);
            naviName.setText(PreferenceUtil.getString(this, Const.PREF_KEY_NAME));
            naviLogin.setText(getTempResources().getString(R.string.navigation_logout));
        } else {
            naviName.setText(getTempResources().getString(R.string.navigation_login));
        }
    }

    private void setMapView(Bundle savedInstanceState) {
        couponMapView.onCreate(savedInstanceState);
        couponMapView.getMapAsync(this);
    }

    private void setGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()) //Use app context to prevent leaks using activity
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    private void setListener() {
        // 상단 탭바
        topTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        // 네비 열기
        mainNaviImg.setOnClickListener(this);
        // 네비 닫기
        naviImageBack.setOnClickListener(this);
        // 네비 닫기
        naviClose.setOnClickListener(this);
        // 프로필
        naviMenuProfile.setOnClickListener(this);
        // 프로필
        naviProfileImage.setOnClickListener(this);
        // 로그인 & 로그아웃
        naviLogin.setOnClickListener(this);
        // 내 기념일
        naviMenuMyDDay.setOnClickListener(this);
        // 쿠폰 요청
        naviMenuRequest.setOnClickListener(this);
        // 내 쿠폰함
        naviMenuMyCoupon.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainNaviImg: openDrawer(); break;
            case R.id.naviClose: closeDrawer(); break;
            case R.id.naviImageBack: closeDrawer(); break;
            case R.id.naviProfileImage: goProfile(); break;
            case R.id.naviMenuProfile: goProfile(); break;
            case R.id.naviMenuMyDDay: goMyDday(); break;
            case R.id.naviLogin: signInAndOut(); break;
            case R.id.naviMenuMyCoupon:
                GoUtil.startActivityForResult(MainActivity.this, FavoriteActivity.class, Const.REQ_CODE_FAVORITE);
                break;
            case R.id.naviMenuRequest:
                GoUtil.startActivity(v.getContext(), RequestCouponActivity.class);
                break;
        }
    }

    CustomTabIndicatorLayout.OnTabSelectedListener onTabSelectedListener = new CustomTabIndicatorLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(CustomTabIndicatorLayout.Tab tab) {
            int curPos = tab.getPosition();
            Log.e("현재위치",curPos+"");
            setCurrentPosition(curPos);
            setCurrentData(curPos);
        }

        @Override
        public void onTabUnselected(CustomTabIndicatorLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(CustomTabIndicatorLayout.Tab tab) {

        }
    };

    private void setCurrentPosition(int position) {
        currentPosition = position;
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private boolean checkSignInStatus() {
        String id = PreferenceUtil.getString(this, Const.PREF_KEY_ID);
        return SignInUtil.hasSignedIn(id);
    }

    private void goProfile() {
        boolean signedIn = checkSignInStatus();
        if (signedIn) {
            GoUtil.startActivity(this, ProfileActivity.class);
            return;
        }
        GoUtil.startActivityForResult(MainActivity.this, SignInActivity.class, Const.REQ_CODE_LOGIN);
    }

    private boolean checkHasId() {
        String id = PreferenceUtil.getString(this, Const.PREF_KEY_ID);
        String tempId = PreferenceUtil.getString(this, Const.PREF_KEY_TEMP_ID);
        return SignInUtil.hasId(id, tempId);
    }

    private void goMyDday() {
        boolean hasId = checkHasId();
        if (!hasId) {
            showSignInDialog();
            return;
        }
        GoUtil.startActivity(this, MyDDayActivity.class);
    }

    private void showSignInDialog(){
        DialogUtil.showDialog(this,
                getResources().getString(R.string.coupon_favorite_my_dday),
                getResources().getStringArray(R.array.coupon_favorite_select_sign_in),
                this, 0);
    }

    private void createTempId(){
        // 1. 임시아이디 생성
        String tempId = DeviceInfoUtil.createTempId();
        // 2. 임시아이디 저장
        saveTempId(tempId);
        // 3. POST
        mainService.createTempMemberInfo(makeTempMember(tempId));
    }

    private void saveTempId(String tempId){
        PreferenceUtil.putString(this, Const.PREF_KEY_TEMP_ID , tempId);
        NoticeUtil.makeToast(this, "임시 아이디가 생성되었습니다");
    }

    private Member makeTempMember(String tempId){
        Member member = new Member();
        member.memberId = tempId;
        return member;
    }

    private void signInAndOut() {
        boolean signedIn = checkSignInStatus();
        if (signedIn) {
            showSignOutDialog();
            return;
        }
        GoUtil.startActivityForResult(MainActivity.this, SignInActivity.class, Const.REQ_CODE_LOGIN);
    }

    private void showSignOutDialog(){
        DialogUtil.showDialog(this,
                getTempResources().getString(R.string.dialog_sign_out),
                this, 0);
    }

    private void setSignOutProfile() {
        naviName.setText("");
        naviName.setVisibility(View.GONE);
        naviLogin.setText(getResources().getString(R.string.navigation_login));
    }

    @Override
    public void onSimplePositiveButton(int id) {
        String memberId = PreferenceUtil.getString(this, Const.PREF_KEY_ID);
        if (memberId.startsWith(Const.PREFIX_KAKAO)) {
            SignInUtil.signOutKakao(this, signOutHandler);
        } else if (memberId.startsWith(Const.PREFIX_GOOGLE)) {
            SignInUtil.signOutGoogle(mGoogleApiClient, this, signOutHandler);
        } else if (memberId.startsWith(Const.PREFIX_FACEBOOK)) {
            SignInUtil.signOutFacebook(this, signOutHandler);
        } else {
            SignInUtil.deleteLocalInfo(this);
            setSignOutProfile();
        }
    }

    @Override
    public void onSimpleNegativeButton(int id) {

    }

    @Override
    public void onSimpleCanceled(int id) {

    }

    @Override
    public void onItemSelected(int id, int which, String[] array) {
        switch (which){
            case Const.MEMBER_SIGN_IN:
                GoUtil.startActivity(this, SignInActivity.class);
                break;
            case Const.TEMP_SIGN_IN:
                createTempId();
                break;
        }
    }

    @Override
    public void onItemCanceled(int id) {

    }

    Handler signOutHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            notifyFavoriteSetChanged();
            setCurrentData(currentPosition);
            setSignOutProfile();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PermissionLocationUtil.getInstance().checkVersion(this, this);
        changeOptionIcon(item);
        return super.onOptionsItemSelected(item);
    }

    private void changeOptionIcon(MenuItem item) {
        if (couponMapView.getVisibility() == View.VISIBLE) {
            item.setIcon(R.drawable.icon_topbar_map_click);
        } else {
            item.setIcon(R.drawable.icon_topbar_map);
        }
    }

    private void setMapVisibility() {
        if (couponMapView.getVisibility() == View.GONE) {
            couponMapView.setVisibility(View.VISIBLE);
            couponRecycler.setVisibility(View.GONE);
        } else {
            couponMapView.setVisibility(View.GONE);
            couponRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GeoUtil.getDefaultLatLng(), 15));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionLocationUtil.getInstance().onResult(grantResults, this);
    }

    @Override
    public void postLocationPermission() {
        setMapVisibility();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setSignInProfile(String name) {
        naviName.setVisibility(View.VISIBLE);
        naviName.setText(name + "님");
        naviLogin.setText(getResources().getString(R.string.navigation_logout));
    }

    /**
     * 맵뷰 생명주기
     */
    @Override
    public void onResume() {
        super.onResume();
        couponMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        couponMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        couponMapView.onDestroy();
    }

    private Resources getTempResources() {
        if (resources == null)
            resources = getResources();
        return resources;
    }

    private String[] getMainCategoryArray() {
        if (mainCategoryArray == null)
            mainCategoryArray = getTempResources().getStringArray(R.array.main_category);
        return mainCategoryArray;
    }

    private String[] getCategoryArray() {
        if (categoryArray == null)
            categoryArray = getTempResources().getStringArray(R.array.category);
        return categoryArray;
    }

    private CouponDao getCouponDao() {
        if (couponDao == null)
            couponDao = CouponDao.getInstance(this);
        return couponDao;
    }

    private void setCurrentData(int index) {
        if (adapter != null) {
            List<Coupon> categorizedCoupon = getCategorizedCouponList(index);
            adapter.setData(categorizedCoupon);
            showNoData(categorizedCoupon);
        }
    }

    private void showNoData(List<Coupon> categorizedCoupon) {
        if (categorizedCoupon.size() == 0) {
            couponRecycler.setVisibility(View.GONE);
            textNoData.setVisibility(View.VISIBLE);
        } else {
            couponRecycler.setVisibility(View.VISIBLE);
            textNoData.setVisibility(View.GONE);
        }
    }

    private void initData(){
        mapCategoryAndList();
        mapCategoryAndHasChange();
    }

    private void mapCategoryAndList(){
        String[] mainCategory = getMainCategoryArray();
        categorizedListMap = new HashMap<>();
        categorizedListMap.put(mainCategory[0], totalList);
        categorizedListMap.put(mainCategory[1], ticketList);
        categorizedListMap.put(mainCategory[2], travelList);
        categorizedListMap.put(mainCategory[3], foodList);
        categorizedListMap.put(mainCategory[4], shoppingList);
        categorizedListMap.put(mainCategory[5], fashionList);
        categorizedListMap.put(mainCategory[6], beautyList);
        categorizedListMap.put(mainCategory[7], sportsList);
        categorizedListMap.put(mainCategory[8], funList);
        categorizedListMap.put(mainCategory[9], studyList);
        categorizedListMap.put(mainCategory[10], etcList);
    }

    private void mapCategoryAndHasChange(){
        String[] mainCategory = getMainCategoryArray();
        hasChangeMap = new HashMap<>();
        hasChangeMap.put(mainCategory[0], totalHaschange);
        hasChangeMap.put(mainCategory[1], ticketHasChange);
        hasChangeMap.put(mainCategory[2], travelHasChange);
        hasChangeMap.put(mainCategory[3], foodHasChange);
        hasChangeMap.put(mainCategory[4], shoppingHasChange);
        hasChangeMap.put(mainCategory[5], fashionHasChange);
        hasChangeMap.put(mainCategory[6], beautyHasChange);
        hasChangeMap.put(mainCategory[7], sportsHasChange);
        hasChangeMap.put(mainCategory[8], funHasChange);
        hasChangeMap.put(mainCategory[9], studyHasChange);
        hasChangeMap.put(mainCategory[10], etcHasChange);
    }

    private List<Coupon> getCategorizedCouponList(int index) {
        CouponDao dao = getCouponDao();
        String[] mainCategoryArray = getMainCategoryArray();
        String mainCategory = mainCategoryArray[index];
        switch (index){
            case Const.MAIN_CATEGORY_ALL:
                if(categorizedListMap.get(mainCategory) == null || hasChangeMap.get(mainCategory) == true){
                    List<Coupon> categorizedList = dao.readAll();
                    categorizedListMap.put(mainCategory, categorizedList);
                    hasChangeMap.put(mainCategory, false);
                    return categorizedList;
                }
            default:
                if(categorizedListMap.get(mainCategory) == null || hasChangeMap.get(mainCategory) == true){
                    List<Coupon> categorizedList = dao.readByValue(Const.COLUMN_MAIN_CATEGORY, mainCategory);
                    categorizedListMap.put(mainCategory, categorizedList);
                    hasChangeMap.put(mainCategory, false);
                    return categorizedList;
                }
        }
        return categorizedListMap.get(mainCategory);
    }

    // 메인에서 즐겨찾기 추가
    public void notifyMainFavoriteChanged(String mainCategory) {
        if (currentPosition == 0) {
            notifyFavoriteChanged(mainCategory);
        } else {
            notifyCategoryAllChanged();
        }
    }

    // 특정 카테고리
    public void notifyFavoriteChanged(String mainCategory) {

        hasChangeMap.put(mainCategory, true);
    }

    // 전체 카테고리만
    public void notifyCategoryAllChanged(){
        hasChangeMap.put(getMainCategoryArray()[0], true);
    }

    // 카테고리 전체
    public void notifyFavoriteSetChanged(){
        for(String category : getMainCategoryArray()){
            hasChangeMap.put(category, true);
        }
    }

    // 카테고리 몇 개
    public void notifyFavoriteSetChanged(String[] changedCategory){
        for(String category : changedCategory){
            notifyFavoriteChanged(category);
        }
        notifyCategoryAllChanged();
    }

    /**
     * 로그인 결과값, 삭제된 쿠폰 반영
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.REQ_CODE_LOGIN && resultCode == RESULT_OK) {
            setSignInProfile(data.getStringExtra(Const.GOUTIL_EXTRA_2));
            boolean hasChange = data.getBooleanExtra(Const.GOUTIL_EXTRA_1, false);
            if (hasChange) {
                notifyFavoriteSetChanged();
                setCurrentData(currentPosition);
            }
        } else if (requestCode == Const.REQ_CODE_FAVORITE && resultCode == RESULT_OK) {
            boolean hasChange = data.getBooleanExtra(Const.GOUTIL_EXTRA_1, false);
            if (hasChange) {
                notifyFavoriteSetChanged(data.getStringArrayExtra(Const.GOUTIL_EXTRA_2));
                setCurrentData(currentPosition);
            }
        }
    }

}


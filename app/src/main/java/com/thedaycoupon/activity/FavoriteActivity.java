package com.thedaycoupon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.FavoriteService;
import com.thedaycoupon.adapter.FavoriteAdapter;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.favorite.Favorite;
import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.domain.tempFavorite.TempFavorite;
import com.thedaycoupon.domain.tempFavorite.TempFavoriteDao;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.LogUtil;
import com.thedaycoupon.util.NetworkUtil;
import com.thedaycoupon.util.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteActivity extends BaseActivity
        implements FavoriteAdapter.OnFavoriteCallback, FavoriteService.IFavoriteService {

    private String TAG = getClass().getSimpleName();
    private ImageView favoriteBack;
    private TextView favoriteTitle;
    private RecyclerView favoriteRecycler;
    private List<Favorite> favoriteList;
    private Set<String> categorySet;
    private Intent intent;
    private FavoriteService favoriteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        init();
    }

    private void init() {
        initView();
        setFont();
        setListener();
        setRecyclerView();
        checkUnPostedLeftOvers();
    }

    private void initView() {
        favoriteBack = findViewById(R.id.favoriteBack);
        favoriteTitle = findViewById(R.id.favoriteTitle);
        favoriteRecycler = findViewById(R.id.favoriteRecycler);
    }

    private void setFont() {
        StringUtil.setTypefaceNanumL(this, favoriteTitle);
    }

    private void setListener() {
        favoriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setRecyclerView() {
        favoriteService = new FavoriteService(this, this);
        FavoriteAdapter adapter = new FavoriteAdapter(getFavoriteCoupon(), this, this, favoriteService);
        favoriteRecycler.setAdapter(adapter);
        favoriteRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    // TODO join 하시오
    private List<Coupon> getFavoriteCoupon() {
        favoriteList = FavoriteDao.getInstance(this).readAll();
        List<Coupon> couponList = new ArrayList<>();
        CouponDao lab = CouponDao.getInstance(this);
        for (Favorite favorite : favoriteList) {
            couponList.add(lab.readByValue(Const.COLUMN_NO, favorite.parentNo).get(0));
        }
        return couponList;
    }

    /**
     * 미반영 즐겨찾기 POST
     */
    public void checkUnPostedLeftOvers() {
        if (!NetworkUtil.isConnectedSimple(this)) return;
        List<Favorite> leftOvers = FavoriteDao.getInstance(this).readByValue(Const.COLUMN_ON_SERVER, 0);
        if (leftOvers.size() == 0) {
            LogUtil.e(TAG, "POST 할 leftOver 없음");
            checkUnDeleteLeftOvers();
            return;
        }
        favoriteService.loadLeftOvers(leftOvers);
    }

    /**
     * 미삭제 즐겨찾기 DELETE
     */
    @Override
    public void checkUnDeleteLeftOvers() {
        List<TempFavorite> tempFavorite = TempFavoriteDao.getInstance(this).readAll();
        if (tempFavorite.size() == 0) {
            LogUtil.e(TAG, "DELETE 할 leftOver 없음");
            return;
        }
        favoriteService.deleteLeftOver(tempFavorite);
    }

    /**
     * 메인화면 뷰 갱신
     */
    @Override
    public void setHasChange(boolean hasChange) {
        getTempIntent().putExtra(Const.GOUTIL_EXTRA_1, hasChange);
        setResult(RESULT_OK, intent);
    }

    /**
     * 메인화면 뷰 갱신
     */
    @Override
    public void addChangedCategory(String mainCategory) {
        getCategory().add(mainCategory);
        getTempIntent().putExtra(Const.GOUTIL_EXTRA_2, hashsetToArray());
    }

    private Intent getTempIntent() {
        if (intent == null)
            intent = new Intent();
        return intent;
    }

    private Set<String> getCategory() {
        if (categorySet == null)
            categorySet = new HashSet<>();
        return categorySet;
    }

    private String[] hashsetToArray() {
        String[] categoryArray = new String[getCategory().size()];
        getCategory().toArray(categoryArray);
        return categoryArray;
    }
}

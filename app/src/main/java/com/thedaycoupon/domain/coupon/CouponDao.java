package com.thedaycoupon.domain.coupon;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.thedaycoupon.domain.DBHelper.DBHelper;
import com.thedaycoupon.util.Const;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */
public class CouponDao {

    private static CouponDao sCouponLab;
    private DBHelper helper;
    private Dao<Coupon, Integer> dao;
    private Context context;
    private String TAG = getClass().getSimpleName();

    public CouponDao(Context context) {
        this.context = context;
        helper = new DBHelper(context);
        try {
            dao = helper.getDao(Coupon.class);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    public static CouponDao getInstance(Context context) {
        if (sCouponLab == null) {
            sCouponLab = new CouponDao(context);
        }
        return sCouponLab;
    }

    /**
     * 데이터 저장
     */
    public void create(List<Coupon> couponList) {
        try {
            dao.create(couponList);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 전체 데이터 조회
     */
    public List<Coupon> readAll() {
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponList = dao.queryForAll();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        return couponList;
    }

    /**
     * 값으로 전체 검색
     */
    public List<Coupon> readByValue(String columnName, String value) {
        QueryBuilder<Coupon, Integer> builder = dao.queryBuilder();
        List<Coupon> datas = null;
        try {
            datas = builder.where().eq(columnName, value).query();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        return datas;
    }

    /**
     * 값으로 전체 검색
     */
    public List<Coupon> readByValue(String columnName, int value) {
        QueryBuilder<Coupon, Integer> builder = dao.queryBuilder();
        List<Coupon> datas = null;
        try {
            datas = builder.where().eq(columnName, value).query();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        return datas;
    }

    /**
     * 즐겨찾기 check
     */
    public void updateChecked(int parentNo, boolean value) {
        UpdateBuilder<Coupon, Integer> builder = dao.updateBuilder();
        try {
            builder.where().eq(Const.COLUMN_NO, parentNo);
            builder.updateColumnValue(Const.COLUMN_IS_FAVORITE, value);
            builder.update();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 즐겨찾기 unCheck
     */
    public void updateUnChecked() {
        UpdateBuilder<Coupon, Integer> builder = dao.updateBuilder();
        try {
            builder.updateColumnValue(Const.COLUMN_IS_FAVORITE, false);
            builder.update();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 데이터 전부 삭제
     */
    public void deleteAll() {
        List<Coupon> couponList = readAll();
        try {
            dao.delete(couponList);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

}

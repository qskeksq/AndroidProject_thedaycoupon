package com.thedaycoupon.domain.tempFavorite;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.thedaycoupon.domain.DBHelper.DBHelper;
import com.thedaycoupon.util.Const;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */

public class  TempFavoriteDao {

    private static TempFavoriteDao sTempFavoriteLab;
    private DBHelper helper;
    private Dao<TempFavorite, Integer> dao;
    private Context context;

    public TempFavoriteDao(Context context) {
        this.context = context;
        helper = new DBHelper(context);
        try {
            dao = helper.getDao(TempFavorite.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TempFavoriteDao getInstance(Context context) {
        if (sTempFavoriteLab == null) {
            sTempFavoriteLab = new TempFavoriteDao(context);
        }
        return sTempFavoriteLab;
    }

    /**
     * 데이터 저장
     */
    public void create(TempFavorite tempFavorite) {
        try {
            dao.createIfNotExists(tempFavorite);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }


    /**
     * 전체 데이터 조회
     */
    public List<TempFavorite> readAll() {
        List<TempFavorite> tempFavoriteList = new ArrayList<>();
        try {
            tempFavoriteList = dao.queryForAll();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        return tempFavoriteList;
    }


    /**
     * 데이터 전부 삭제
     */
    public void deleteAll() {
        List<TempFavorite> favoriteList = readAll();
        try {
            dao.delete(favoriteList);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 데이터 삭제
     */
    public void delete(int no) {
        DeleteBuilder<TempFavorite, Integer> builder = dao.deleteBuilder();
        try {
            builder.where().eq(Const.COLUMN_PARENT_NO, no);
            builder.delete();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

}

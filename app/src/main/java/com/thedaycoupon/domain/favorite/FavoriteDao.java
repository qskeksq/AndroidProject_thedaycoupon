package com.thedaycoupon.domain.favorite;

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
public class FavoriteDao {

    private static FavoriteDao sFavoriteLab;
    private DBHelper helper;
    private Dao<Favorite, Integer> dao;
    private Context context;

    public FavoriteDao(Context context) {
        this.context = context;
        helper = new DBHelper(context);
        try {
            dao = helper.getDao(Favorite.class);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    public static FavoriteDao getInstance(Context context) {
        if (sFavoriteLab == null) {
            sFavoriteLab = new FavoriteDao(context);
        }
        return sFavoriteLab;
    }

    /**
     * 데이터 저장
     */
    public void create(Favorite favorite) {
        try {
            dao.createIfNotExists(favorite);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    public void create(List<Favorite> favoriteList) {
        try {
            dao.create(favoriteList);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 전체 데이터 조회
     */
    public List<Favorite> readAll() {
        List<Favorite> favoriteList = new ArrayList<>();
        try {
            favoriteList = dao.queryForAll();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        return favoriteList;
    }

    public List<Favorite> readByValue(String columnName, int value) {
        QueryBuilder<Favorite, Integer> builder = dao.queryBuilder();
        List<Favorite> datas = null;
        try {
            datas = builder.where().eq(columnName, value).query();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        return datas;
    }


    /**
     * 한 개 on off Server
     */
    public void updateOnOffServer(int parentNo, int onOff) {
        UpdateBuilder<Favorite, Integer> builder = dao.updateBuilder();
        try {
            builder.where().eq(Const.COLUMN_PARENT_NO, parentNo);
            builder.updateColumnValue(Const.COLUMN_ON_SERVER, onOff);
            builder.update();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 전체 on off Server
     */
    public void updateOnOffServer(int onOff) {
        UpdateBuilder<Favorite, Integer> builder = dao.updateBuilder();
        try {
            builder.updateColumnValue(Const.COLUMN_ON_SERVER, onOff);
            builder.update();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 업데이트
     */
    public void updateMemberId(String memberId) {
        UpdateBuilder<Favorite, Integer> builder = dao.updateBuilder();
        try {
            builder.updateColumnValue(Const.COLUMN_MEMBER_ID, memberId);
            builder.update();
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 데이터 전부 삭제
     */
    public void deleteAll() {
        List<Favorite> favoriteList = readAll();
        try {
            dao.delete(favoriteList);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 데이터 삭제
     */
    public void delete(int parentNo) {
//        DeleteBuilder<Favorite, Integer> builder = dao.deleteBuilder();
        try {
//            builder.where().eq(Const.COLUMN_PARENT_NO, parentNo);
//            builder.delete();
            dao.deleteById(parentNo);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }

}

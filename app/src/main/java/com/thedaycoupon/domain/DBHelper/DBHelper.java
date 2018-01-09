package com.thedaycoupon.domain.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.favorite.Favorite;
import com.thedaycoupon.domain.tempFavorite.TempFavorite;
import com.thedaycoupon.util.Const;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017-11-14.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public DBHelper(Context context) {
        super(context, Const.LOCAL_DB_NAME, null, Const.LOCAL_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Coupon.class);
            TableUtils.createTable(connectionSource, Favorite.class);
            TableUtils.createTable(connectionSource, TempFavorite.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}

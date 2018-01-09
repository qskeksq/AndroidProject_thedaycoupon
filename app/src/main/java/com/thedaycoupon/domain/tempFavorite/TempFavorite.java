package com.thedaycoupon.domain.tempFavorite;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.PreferenceUtil;
import com.thedaycoupon.util.SignInUtil;

/**
 * Created by Administrator on 2017-11-21.
 */
@DatabaseTable(tableName = "temp_favorite")
public class TempFavorite {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    @SerializedName("member_id") public String memberId;
    @DatabaseField
    @SerializedName("parent_no") public int parentNo;

    public TempFavorite() {

    }

    public TempFavorite(int parentNo, Context context) {
        this.parentNo = parentNo;
        memberId = PreferenceUtil.getString(context, Const.PREF_KEY_ID);
        if(SignInUtil.isEmpty(memberId)){
            memberId = PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID);
        }
    }
}

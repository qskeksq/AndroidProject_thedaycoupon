package com.thedaycoupon.domain.favorite;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017-11-21.
 */
@DatabaseTable(tableName = "favorite_info")
public class Favorite {

    @DatabaseField
    @SerializedName("member_id") public String memberId;
    @DatabaseField(id = true)
    @SerializedName("parent_no") public int parentNo;
    @SerializedName("reg_date") public String regDate;
    @DatabaseField
    @SerializedName("on_server") public int onServer;
}

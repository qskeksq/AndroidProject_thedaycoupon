package com.thedaycoupon.domain.coupon;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017-11-11.
 */
@DatabaseTable(tableName = "table_coupon")
public class Coupon {

    @DatabaseField(id = true)
    public int no;
    @DatabaseField
    @SerializedName("the_day") public String theDay;
    @DatabaseField
    @SerializedName("main_category") public String mainCategory;
    @DatabaseField
    @SerializedName("sub_category") public String subCategory;
    @DatabaseField
    public String name;
    @DatabaseField
    public String homepage;
    @DatabaseField
    public String title;
    @DatabaseField
    public String description;
    @DatabaseField
    @SerializedName("start_date") public String startDate;
    @DatabaseField
    @SerializedName("end_date") public String endDate;
    @DatabaseField
    public String target;
    @DatabaseField
    public String participate;
    @DatabaseField
    public String info;
    @DatabaseField
    @SerializedName("discount_rate") public String discountRate;
    @DatabaseField
    public String notice;
    @DatabaseField
    public String address;
    @DatabaseField
    public double longitude;
    @DatabaseField
    public double latitude;
    @DatabaseField
    @SerializedName("business_hour") public String businessHour;
    @DatabaseField
    public String price;
    @DatabaseField
    public String tel;
    @DatabaseField
    @SerializedName("favorite_count") public int favoriteCount;
    @DatabaseField
    @SerializedName("reg_date") public String regDate;
    @DatabaseField
    public String status;
    @DatabaseField
    @SerializedName("logo_filename") public String logoFilename;
    @DatabaseField
    public boolean isFavorite;
    @DatabaseField
    public transient String logoFilePath;

    public Coupon() {

    }

    @Override
    public String toString()
    {
        return "ClassPojo [discount_rate = "+discountRate+", status = "+status+", no = "+no+", tel = "+tel+", logo_filename = "+logoFilename+", reg_date = "+regDate+", main_category = "+mainCategory+", homepage = "+homepage+", info = "+info+", business_hour = "+businessHour+", title = "+title+", end_date = "+endDate+", price = "+price+", address = "+address+", description = "+description+", name = "+name+", target = "+target+", favorite_count = "+favoriteCount+", longitude = "+longitude+", latitude = "+latitude+", participate = "+participate+", notice = "+notice+", sub_category = "+subCategory+", start_date = "+startDate+", the_day = "+theDay+"]";
    }
}

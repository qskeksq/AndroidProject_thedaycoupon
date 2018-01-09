package com.thedaycoupon.domain.member;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-11-21.
 */
public class Member {

    public int no;
    @SerializedName("member_id") public String memberId;
    public String username;
    public double latitude;
    public double longitude;
    @SerializedName("reg_date") public String regDate;
    public String password;


    @Override
    public String toString()
    {
        return "ClassPojo [no = "+no+", longitude = "+longitude+", latitude = "+latitude+", reg_date = "+regDate+", password = "+password+"]";
    }
}

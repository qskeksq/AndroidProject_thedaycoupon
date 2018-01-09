package com.thedaycoupon.domain.wrongInfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2017. 12. 11..
 */

public class WrongInfo {

    public String title;
    public String content;
    public String email;
    public String tel;
    @SerializedName("parent_no") public int parentNo;

    public WrongInfo() {

    }
}

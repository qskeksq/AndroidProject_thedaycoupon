package com.thedaycoupon.domain.logoImage;

/**
 * Created by Administrator on 2017-11-17.
 */

public class LogoImage {

    public String type;
    public byte[] data;

    public LogoImage() {

    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", type = "+type+"]";
    }

}

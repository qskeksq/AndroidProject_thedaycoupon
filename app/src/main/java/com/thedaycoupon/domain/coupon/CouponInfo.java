package com.thedaycoupon.domain.coupon;

/**
 * Created by Administrator on 2017-12-05.
 */

public class CouponInfo {

    private RESULT RESULT;

    private String totalCount;

    private Coupon[] coupon;

    public RESULT getRESULT() {
        return RESULT;
    }

    public void setRESULT(RESULT RESULT) {
        this.RESULT = RESULT;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public Coupon[] getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon[] coupon) {
        this.coupon = coupon;
    }

    @Override
    public String toString() {
        return "ClassPojo [RESULT = " + RESULT + ", totalCount = " + totalCount + ", coupon = " + coupon + "]";
    }
}

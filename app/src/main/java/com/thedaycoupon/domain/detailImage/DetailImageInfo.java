package com.thedaycoupon.domain.detailImage;

/**
 * Created by Administrator on 2017-12-05.
 */

public class DetailImageInfo {

    private RESULT RESULT;

    private String totalCount;

    private DetailImage[] detailImage;

    public RESULT getRESULT ()
    {
        return RESULT;
    }

    public void setRESULT (RESULT RESULT)
    {
        this.RESULT = RESULT;
    }

    public String getTotalCount ()
    {
        return totalCount;
    }

    public void setTotalCount (String totalCount)
    {
        this.totalCount = totalCount;
    }

    public DetailImage[] getDetailImage ()
    {
        return detailImage;
    }

    public void setDetailImage (DetailImage[] detailImage)
    {
        this.detailImage = detailImage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RESULT = "+RESULT+", totalCount = "+totalCount+", detailImage = "+detailImage+"]";
    }
}

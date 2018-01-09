package com.thedaycoupon.domain.logoImage;

/**
 * Created by Administrator on 2017-12-05.
 */

public class LogoImageInfo {

    private LogoImage logoImage;

    private RESULT RESULT;

    private String totalCount;

    public LogoImage getLogoImage ()
    {
        return logoImage;
    }

    public void setLogoImage (LogoImage logoImage)
    {
        this.logoImage = logoImage;
    }

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

    @Override
    public String toString()
    {
        return "ClassPojo [logoImage = "+logoImage+", RESULT = "+RESULT+", totalCount = "+totalCount+"]";
    }

}

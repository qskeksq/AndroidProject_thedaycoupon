package com.thedaycoupon.domain.version;

/**
 * Created by Administrator on 2017-12-05.
 */

public class VersionInfo {

    private RESULT RESULT;

    private String totalCount;

    private String version;

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

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RESULT = "+RESULT+", totalCount = "+totalCount+", version = "+version+"]";
    }

}

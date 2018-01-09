package com.thedaycoupon.domain.wrongInfo;

/**
 * Created by mac on 2017. 12. 11..
 */

public class WrongInfoInfo {

    private String[] wrongInfo;

    private RESULT RESULT;

    private String totalCount;

    public String[] getWrongInfo ()
    {
        return wrongInfo;
    }

    public void setWrongInfo (String[] wrongInfo)
    {
        this.wrongInfo = wrongInfo;
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
        return "ClassPojo [wrongInfo = "+wrongInfo+", RESULT = "+RESULT+", totalCount = "+totalCount+"]";
    }

}
